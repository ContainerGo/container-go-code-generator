import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarrier, NewCarrier } from '../carrier.model';

export type PartialUpdateCarrier = Partial<ICarrier> & Pick<ICarrier, 'id'>;

export type EntityResponseType = HttpResponse<ICarrier>;
export type EntityArrayResponseType = HttpResponse<ICarrier[]>;

@Injectable({ providedIn: 'root' })
export class CarrierService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carriers');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(carrier: NewCarrier): Observable<EntityResponseType> {
    return this.http.post<ICarrier>(this.resourceUrl, carrier, { observe: 'response' });
  }

  update(carrier: ICarrier): Observable<EntityResponseType> {
    return this.http.put<ICarrier>(`${this.resourceUrl}/${this.getCarrierIdentifier(carrier)}`, carrier, { observe: 'response' });
  }

  partialUpdate(carrier: PartialUpdateCarrier): Observable<EntityResponseType> {
    return this.http.patch<ICarrier>(`${this.resourceUrl}/${this.getCarrierIdentifier(carrier)}`, carrier, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarrier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarrier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarrierIdentifier(carrier: Pick<ICarrier, 'id'>): number {
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
      const carrierCollectionIdentifiers = carrierCollection.map(carrierItem => this.getCarrierIdentifier(carrierItem)!);
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
}
