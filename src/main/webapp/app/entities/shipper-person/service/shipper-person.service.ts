import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipperPerson, NewShipperPerson } from '../shipper-person.model';

export type PartialUpdateShipperPerson = Partial<IShipperPerson> & Pick<IShipperPerson, 'id'>;

export type EntityResponseType = HttpResponse<IShipperPerson>;
export type EntityArrayResponseType = HttpResponse<IShipperPerson[]>;

@Injectable({ providedIn: 'root' })
export class ShipperPersonService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipper-people');

  create(shipperPerson: NewShipperPerson): Observable<EntityResponseType> {
    return this.http.post<IShipperPerson>(this.resourceUrl, shipperPerson, { observe: 'response' });
  }

  update(shipperPerson: IShipperPerson): Observable<EntityResponseType> {
    return this.http.put<IShipperPerson>(`${this.resourceUrl}/${this.getShipperPersonIdentifier(shipperPerson)}`, shipperPerson, {
      observe: 'response',
    });
  }

  partialUpdate(shipperPerson: PartialUpdateShipperPerson): Observable<EntityResponseType> {
    return this.http.patch<IShipperPerson>(`${this.resourceUrl}/${this.getShipperPersonIdentifier(shipperPerson)}`, shipperPerson, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShipperPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipperPerson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipperPersonIdentifier(shipperPerson: Pick<IShipperPerson, 'id'>): number {
    return shipperPerson.id;
  }

  compareShipperPerson(o1: Pick<IShipperPerson, 'id'> | null, o2: Pick<IShipperPerson, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipperPersonIdentifier(o1) === this.getShipperPersonIdentifier(o2) : o1 === o2;
  }

  addShipperPersonToCollectionIfMissing<Type extends Pick<IShipperPerson, 'id'>>(
    shipperPersonCollection: Type[],
    ...shipperPeopleToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipperPeople: Type[] = shipperPeopleToCheck.filter(isPresent);
    if (shipperPeople.length > 0) {
      const shipperPersonCollectionIdentifiers = shipperPersonCollection.map(shipperPersonItem =>
        this.getShipperPersonIdentifier(shipperPersonItem),
      );
      const shipperPeopleToAdd = shipperPeople.filter(shipperPersonItem => {
        const shipperPersonIdentifier = this.getShipperPersonIdentifier(shipperPersonItem);
        if (shipperPersonCollectionIdentifiers.includes(shipperPersonIdentifier)) {
          return false;
        }
        shipperPersonCollectionIdentifiers.push(shipperPersonIdentifier);
        return true;
      });
      return [...shipperPeopleToAdd, ...shipperPersonCollection];
    }
    return shipperPersonCollection;
  }
}
