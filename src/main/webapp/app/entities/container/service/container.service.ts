import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContainer, NewContainer } from '../container.model';

export type PartialUpdateContainer = Partial<IContainer> & Pick<IContainer, 'id'>;

type RestOf<T extends IContainer | NewContainer> = Omit<
  T,
  'pickupFromDate' | 'dropoffUntilDate' | 'biddingFromDate' | 'biddingUntilDate'
> & {
  pickupFromDate?: string | null;
  dropoffUntilDate?: string | null;
  biddingFromDate?: string | null;
  biddingUntilDate?: string | null;
};

export type RestContainer = RestOf<IContainer>;

export type NewRestContainer = RestOf<NewContainer>;

export type PartialUpdateRestContainer = RestOf<PartialUpdateContainer>;

export type EntityResponseType = HttpResponse<IContainer>;
export type EntityArrayResponseType = HttpResponse<IContainer[]>;

@Injectable({ providedIn: 'root' })
export class ContainerService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/containers');

  create(container: NewContainer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(container);
    return this.http
      .post<RestContainer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(container: IContainer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(container);
    return this.http
      .put<RestContainer>(`${this.resourceUrl}/${this.getContainerIdentifier(container)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(container: PartialUpdateContainer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(container);
    return this.http
      .patch<RestContainer>(`${this.resourceUrl}/${this.getContainerIdentifier(container)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestContainer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestContainer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContainerIdentifier(container: Pick<IContainer, 'id'>): string {
    return container.id;
  }

  compareContainer(o1: Pick<IContainer, 'id'> | null, o2: Pick<IContainer, 'id'> | null): boolean {
    return o1 && o2 ? this.getContainerIdentifier(o1) === this.getContainerIdentifier(o2) : o1 === o2;
  }

  addContainerToCollectionIfMissing<Type extends Pick<IContainer, 'id'>>(
    containerCollection: Type[],
    ...containersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const containers: Type[] = containersToCheck.filter(isPresent);
    if (containers.length > 0) {
      const containerCollectionIdentifiers = containerCollection.map(containerItem => this.getContainerIdentifier(containerItem));
      const containersToAdd = containers.filter(containerItem => {
        const containerIdentifier = this.getContainerIdentifier(containerItem);
        if (containerCollectionIdentifiers.includes(containerIdentifier)) {
          return false;
        }
        containerCollectionIdentifiers.push(containerIdentifier);
        return true;
      });
      return [...containersToAdd, ...containerCollection];
    }
    return containerCollection;
  }

  protected convertDateFromClient<T extends IContainer | NewContainer | PartialUpdateContainer>(container: T): RestOf<T> {
    return {
      ...container,
      pickupFromDate: container.pickupFromDate?.toJSON() ?? null,
      dropoffUntilDate: container.dropoffUntilDate?.toJSON() ?? null,
      biddingFromDate: container.biddingFromDate?.toJSON() ?? null,
      biddingUntilDate: container.biddingUntilDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restContainer: RestContainer): IContainer {
    return {
      ...restContainer,
      pickupFromDate: restContainer.pickupFromDate ? dayjs(restContainer.pickupFromDate) : undefined,
      dropoffUntilDate: restContainer.dropoffUntilDate ? dayjs(restContainer.dropoffUntilDate) : undefined,
      biddingFromDate: restContainer.biddingFromDate ? dayjs(restContainer.biddingFromDate) : undefined,
      biddingUntilDate: restContainer.biddingUntilDate ? dayjs(restContainer.biddingUntilDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestContainer>): HttpResponse<IContainer> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestContainer[]>): HttpResponse<IContainer[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
