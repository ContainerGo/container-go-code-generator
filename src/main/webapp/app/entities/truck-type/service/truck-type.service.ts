import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITruckType, NewTruckType } from '../truck-type.model';

export type PartialUpdateTruckType = Partial<ITruckType> & Pick<ITruckType, 'id'>;

export type EntityResponseType = HttpResponse<ITruckType>;
export type EntityArrayResponseType = HttpResponse<ITruckType[]>;

@Injectable({ providedIn: 'root' })
export class TruckTypeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/truck-types');

  create(truckType: NewTruckType): Observable<EntityResponseType> {
    return this.http.post<ITruckType>(this.resourceUrl, truckType, { observe: 'response' });
  }

  update(truckType: ITruckType): Observable<EntityResponseType> {
    return this.http.put<ITruckType>(`${this.resourceUrl}/${this.getTruckTypeIdentifier(truckType)}`, truckType, { observe: 'response' });
  }

  partialUpdate(truckType: PartialUpdateTruckType): Observable<EntityResponseType> {
    return this.http.patch<ITruckType>(`${this.resourceUrl}/${this.getTruckTypeIdentifier(truckType)}`, truckType, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITruckType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITruckType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTruckTypeIdentifier(truckType: Pick<ITruckType, 'id'>): string {
    return truckType.id;
  }

  compareTruckType(o1: Pick<ITruckType, 'id'> | null, o2: Pick<ITruckType, 'id'> | null): boolean {
    return o1 && o2 ? this.getTruckTypeIdentifier(o1) === this.getTruckTypeIdentifier(o2) : o1 === o2;
  }

  addTruckTypeToCollectionIfMissing<Type extends Pick<ITruckType, 'id'>>(
    truckTypeCollection: Type[],
    ...truckTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const truckTypes: Type[] = truckTypesToCheck.filter(isPresent);
    if (truckTypes.length > 0) {
      const truckTypeCollectionIdentifiers = truckTypeCollection.map(truckTypeItem => this.getTruckTypeIdentifier(truckTypeItem));
      const truckTypesToAdd = truckTypes.filter(truckTypeItem => {
        const truckTypeIdentifier = this.getTruckTypeIdentifier(truckTypeItem);
        if (truckTypeCollectionIdentifiers.includes(truckTypeIdentifier)) {
          return false;
        }
        truckTypeCollectionIdentifiers.push(truckTypeIdentifier);
        return true;
      });
      return [...truckTypesToAdd, ...truckTypeCollection];
    }
    return truckTypeCollection;
  }
}
