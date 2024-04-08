import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipperPersonGroup, NewShipperPersonGroup } from '../shipper-person-group.model';

export type PartialUpdateShipperPersonGroup = Partial<IShipperPersonGroup> & Pick<IShipperPersonGroup, 'id'>;

export type EntityResponseType = HttpResponse<IShipperPersonGroup>;
export type EntityArrayResponseType = HttpResponse<IShipperPersonGroup[]>;

@Injectable({ providedIn: 'root' })
export class ShipperPersonGroupService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipper-person-groups');

  create(shipperPersonGroup: NewShipperPersonGroup): Observable<EntityResponseType> {
    return this.http.post<IShipperPersonGroup>(this.resourceUrl, shipperPersonGroup, { observe: 'response' });
  }

  update(shipperPersonGroup: IShipperPersonGroup): Observable<EntityResponseType> {
    return this.http.put<IShipperPersonGroup>(
      `${this.resourceUrl}/${this.getShipperPersonGroupIdentifier(shipperPersonGroup)}`,
      shipperPersonGroup,
      { observe: 'response' },
    );
  }

  partialUpdate(shipperPersonGroup: PartialUpdateShipperPersonGroup): Observable<EntityResponseType> {
    return this.http.patch<IShipperPersonGroup>(
      `${this.resourceUrl}/${this.getShipperPersonGroupIdentifier(shipperPersonGroup)}`,
      shipperPersonGroup,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IShipperPersonGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipperPersonGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipperPersonGroupIdentifier(shipperPersonGroup: Pick<IShipperPersonGroup, 'id'>): string {
    return shipperPersonGroup.id;
  }

  compareShipperPersonGroup(o1: Pick<IShipperPersonGroup, 'id'> | null, o2: Pick<IShipperPersonGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipperPersonGroupIdentifier(o1) === this.getShipperPersonGroupIdentifier(o2) : o1 === o2;
  }

  addShipperPersonGroupToCollectionIfMissing<Type extends Pick<IShipperPersonGroup, 'id'>>(
    shipperPersonGroupCollection: Type[],
    ...shipperPersonGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipperPersonGroups: Type[] = shipperPersonGroupsToCheck.filter(isPresent);
    if (shipperPersonGroups.length > 0) {
      const shipperPersonGroupCollectionIdentifiers = shipperPersonGroupCollection.map(shipperPersonGroupItem =>
        this.getShipperPersonGroupIdentifier(shipperPersonGroupItem),
      );
      const shipperPersonGroupsToAdd = shipperPersonGroups.filter(shipperPersonGroupItem => {
        const shipperPersonGroupIdentifier = this.getShipperPersonGroupIdentifier(shipperPersonGroupItem);
        if (shipperPersonGroupCollectionIdentifiers.includes(shipperPersonGroupIdentifier)) {
          return false;
        }
        shipperPersonGroupCollectionIdentifiers.push(shipperPersonGroupIdentifier);
        return true;
      });
      return [...shipperPersonGroupsToAdd, ...shipperPersonGroupCollection];
    }
    return shipperPersonGroupCollection;
  }
}
