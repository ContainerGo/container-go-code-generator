import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITruck, NewTruck } from '../truck.model';

export type PartialUpdateTruck = Partial<ITruck> & Pick<ITruck, 'id'>;

export type EntityResponseType = HttpResponse<ITruck>;
export type EntityArrayResponseType = HttpResponse<ITruck[]>;

@Injectable({ providedIn: 'root' })
export class TruckService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/trucks');

  create(truck: NewTruck): Observable<EntityResponseType> {
    return this.http.post<ITruck>(this.resourceUrl, truck, { observe: 'response' });
  }

  update(truck: ITruck): Observable<EntityResponseType> {
    return this.http.put<ITruck>(`${this.resourceUrl}/${this.getTruckIdentifier(truck)}`, truck, { observe: 'response' });
  }

  partialUpdate(truck: PartialUpdateTruck): Observable<EntityResponseType> {
    return this.http.patch<ITruck>(`${this.resourceUrl}/${this.getTruckIdentifier(truck)}`, truck, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITruck>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITruck[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTruckIdentifier(truck: Pick<ITruck, 'id'>): string {
    return truck.id;
  }

  compareTruck(o1: Pick<ITruck, 'id'> | null, o2: Pick<ITruck, 'id'> | null): boolean {
    return o1 && o2 ? this.getTruckIdentifier(o1) === this.getTruckIdentifier(o2) : o1 === o2;
  }

  addTruckToCollectionIfMissing<Type extends Pick<ITruck, 'id'>>(
    truckCollection: Type[],
    ...trucksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trucks: Type[] = trucksToCheck.filter(isPresent);
    if (trucks.length > 0) {
      const truckCollectionIdentifiers = truckCollection.map(truckItem => this.getTruckIdentifier(truckItem));
      const trucksToAdd = trucks.filter(truckItem => {
        const truckIdentifier = this.getTruckIdentifier(truckItem);
        if (truckCollectionIdentifiers.includes(truckIdentifier)) {
          return false;
        }
        truckCollectionIdentifiers.push(truckIdentifier);
        return true;
      });
      return [...trucksToAdd, ...truckCollection];
    }
    return truckCollection;
  }
}
