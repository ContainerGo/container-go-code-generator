import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContainerStatus, NewContainerStatus } from '../container-status.model';

export type PartialUpdateContainerStatus = Partial<IContainerStatus> & Pick<IContainerStatus, 'id'>;

export type EntityResponseType = HttpResponse<IContainerStatus>;
export type EntityArrayResponseType = HttpResponse<IContainerStatus[]>;

@Injectable({ providedIn: 'root' })
export class ContainerStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/container-statuses');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(containerStatus: NewContainerStatus): Observable<EntityResponseType> {
    return this.http.post<IContainerStatus>(this.resourceUrl, containerStatus, { observe: 'response' });
  }

  update(containerStatus: IContainerStatus): Observable<EntityResponseType> {
    return this.http.put<IContainerStatus>(`${this.resourceUrl}/${this.getContainerStatusIdentifier(containerStatus)}`, containerStatus, {
      observe: 'response',
    });
  }

  partialUpdate(containerStatus: PartialUpdateContainerStatus): Observable<EntityResponseType> {
    return this.http.patch<IContainerStatus>(`${this.resourceUrl}/${this.getContainerStatusIdentifier(containerStatus)}`, containerStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContainerStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContainerStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContainerStatusIdentifier(containerStatus: Pick<IContainerStatus, 'id'>): number {
    return containerStatus.id;
  }

  compareContainerStatus(o1: Pick<IContainerStatus, 'id'> | null, o2: Pick<IContainerStatus, 'id'> | null): boolean {
    return o1 && o2 ? this.getContainerStatusIdentifier(o1) === this.getContainerStatusIdentifier(o2) : o1 === o2;
  }

  addContainerStatusToCollectionIfMissing<Type extends Pick<IContainerStatus, 'id'>>(
    containerStatusCollection: Type[],
    ...containerStatusesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const containerStatuses: Type[] = containerStatusesToCheck.filter(isPresent);
    if (containerStatuses.length > 0) {
      const containerStatusCollectionIdentifiers = containerStatusCollection.map(
        containerStatusItem => this.getContainerStatusIdentifier(containerStatusItem)!,
      );
      const containerStatusesToAdd = containerStatuses.filter(containerStatusItem => {
        const containerStatusIdentifier = this.getContainerStatusIdentifier(containerStatusItem);
        if (containerStatusCollectionIdentifiers.includes(containerStatusIdentifier)) {
          return false;
        }
        containerStatusCollectionIdentifiers.push(containerStatusIdentifier);
        return true;
      });
      return [...containerStatusesToAdd, ...containerStatusCollection];
    }
    return containerStatusCollection;
  }
}
