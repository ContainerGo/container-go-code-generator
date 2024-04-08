import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarrier, NewCarrier } from '../carrier.model';

export type PartialUpdateCarrier = Partial<ICarrier> & Pick<ICarrier, 'id'>;

type RestOf<T extends ICarrier | NewCarrier> = Omit<T, 'verifiedSince'> & {
  verifiedSince?: string | null;
};

export type RestCarrier = RestOf<ICarrier>;

export type NewRestCarrier = RestOf<NewCarrier>;

export type PartialUpdateRestCarrier = RestOf<PartialUpdateCarrier>;

export type EntityResponseType = HttpResponse<ICarrier>;
export type EntityArrayResponseType = HttpResponse<ICarrier[]>;

@Injectable({ providedIn: 'root' })
export class CarrierService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carriers');

  create(carrier: NewCarrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carrier);
    return this.http
      .post<RestCarrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(carrier: ICarrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carrier);
    return this.http
      .put<RestCarrier>(`${this.resourceUrl}/${this.getCarrierIdentifier(carrier)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(carrier: PartialUpdateCarrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carrier);
    return this.http
      .patch<RestCarrier>(`${this.resourceUrl}/${this.getCarrierIdentifier(carrier)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCarrier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCarrier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarrierIdentifier(carrier: Pick<ICarrier, 'id'>): string {
    return carrier.id;
  }

  compareCarrier(o1: Pick<ICarrier, 'id'> | null, o2: Pick<ICarrier, 'id'> | null): boolean {
    return o1 && o2 ? this.getCarrierIdentifier(o1) === this.getCarrierIdentifier(o2) : o1 === o2;
  }

  addCarrierToCollectionIfMissing<Type extends Pick<ICarrier, 'id'>>(
    carrierCollection: Type[],
    ...carriersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const carriers: Type[] = carriersToCheck.filter(isPresent);
    if (carriers.length > 0) {
      const carrierCollectionIdentifiers = carrierCollection.map(carrierItem => this.getCarrierIdentifier(carrierItem));
      const carriersToAdd = carriers.filter(carrierItem => {
        const carrierIdentifier = this.getCarrierIdentifier(carrierItem);
        if (carrierCollectionIdentifiers.includes(carrierIdentifier)) {
          return false;
        }
        carrierCollectionIdentifiers.push(carrierIdentifier);
        return true;
      });
      return [...carriersToAdd, ...carrierCollection];
    }
    return carrierCollection;
  }

  protected convertDateFromClient<T extends ICarrier | NewCarrier | PartialUpdateCarrier>(carrier: T): RestOf<T> {
    return {
      ...carrier,
      verifiedSince: carrier.verifiedSince?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCarrier: RestCarrier): ICarrier {
    return {
      ...restCarrier,
      verifiedSince: restCarrier.verifiedSince ? dayjs(restCarrier.verifiedSince) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCarrier>): HttpResponse<ICarrier> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCarrier[]>): HttpResponse<ICarrier[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
