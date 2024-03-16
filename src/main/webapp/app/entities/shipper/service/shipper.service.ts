import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipper, NewShipper } from '../shipper.model';

export type PartialUpdateShipper = Partial<IShipper> & Pick<IShipper, 'id'>;

export type EntityResponseType = HttpResponse<IShipper>;
export type EntityArrayResponseType = HttpResponse<IShipper[]>;

@Injectable({ providedIn: 'root' })
export class ShipperService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shippers');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(shipper: NewShipper): Observable<EntityResponseType> {
    return this.http.post<IShipper>(this.resourceUrl, shipper, { observe: 'response' });
  }

  update(shipper: IShipper): Observable<EntityResponseType> {
    return this.http.put<IShipper>(`${this.resourceUrl}/${this.getShipperIdentifier(shipper)}`, shipper, { observe: 'response' });
  }

  partialUpdate(shipper: PartialUpdateShipper): Observable<EntityResponseType> {
    return this.http.patch<IShipper>(`${this.resourceUrl}/${this.getShipperIdentifier(shipper)}`, shipper, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShipper>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipper[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipperIdentifier(shipper: Pick<IShipper, 'id'>): number {
    return shipper.id;
  }

  compareShipper(o1: Pick<IShipper, 'id'> | null, o2: Pick<IShipper, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipperIdentifier(o1) === this.getShipperIdentifier(o2) : o1 === o2;
  }

  addShipperToCollectionIfMissing<Type extends Pick<IShipper, 'id'>>(
    shipperCollection: Type[],
    ...shippersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shippers: Type[] = shippersToCheck.filter(isPresent);
    if (shippers.length > 0) {
      const shipperCollectionIdentifiers = shipperCollection.map(shipperItem => this.getShipperIdentifier(shipperItem)!);
      const shippersToAdd = shippers.filter(shipperItem => {
        const shipperIdentifier = this.getShipperIdentifier(shipperItem);
        if (shipperCollectionIdentifiers.includes(shipperIdentifier)) {
          return false;
        }
        shipperCollectionIdentifiers.push(shipperIdentifier);
        return true;
      });
      return [...shippersToAdd, ...shipperCollection];
    }
    return shipperCollection;
  }
}
