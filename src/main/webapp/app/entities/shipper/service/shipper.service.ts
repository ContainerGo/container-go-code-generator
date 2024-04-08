import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipper, NewShipper } from '../shipper.model';

export type PartialUpdateShipper = Partial<IShipper> & Pick<IShipper, 'id'>;

type RestOf<T extends IShipper | NewShipper> = Omit<T, 'contractValidUntil'> & {
  contractValidUntil?: string | null;
};

export type RestShipper = RestOf<IShipper>;

export type NewRestShipper = RestOf<NewShipper>;

export type PartialUpdateRestShipper = RestOf<PartialUpdateShipper>;

export type EntityResponseType = HttpResponse<IShipper>;
export type EntityArrayResponseType = HttpResponse<IShipper[]>;

@Injectable({ providedIn: 'root' })
export class ShipperService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shippers');

  create(shipper: NewShipper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipper);
    return this.http
      .post<RestShipper>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(shipper: IShipper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipper);
    return this.http
      .put<RestShipper>(`${this.resourceUrl}/${this.getShipperIdentifier(shipper)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(shipper: PartialUpdateShipper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipper);
    return this.http
      .patch<RestShipper>(`${this.resourceUrl}/${this.getShipperIdentifier(shipper)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestShipper>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestShipper[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipperIdentifier(shipper: Pick<IShipper, 'id'>): string {
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
      const shipperCollectionIdentifiers = shipperCollection.map(shipperItem => this.getShipperIdentifier(shipperItem));
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

  protected convertDateFromClient<T extends IShipper | NewShipper | PartialUpdateShipper>(shipper: T): RestOf<T> {
    return {
      ...shipper,
      contractValidUntil: shipper.contractValidUntil?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restShipper: RestShipper): IShipper {
    return {
      ...restShipper,
      contractValidUntil: restShipper.contractValidUntil ? dayjs(restShipper.contractValidUntil) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestShipper>): HttpResponse<IShipper> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestShipper[]>): HttpResponse<IShipper[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
