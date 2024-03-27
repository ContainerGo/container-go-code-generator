import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipmentHistory, NewShipmentHistory } from '../shipment-history.model';

export type PartialUpdateShipmentHistory = Partial<IShipmentHistory> & Pick<IShipmentHistory, 'id'>;

type RestOf<T extends IShipmentHistory | NewShipmentHistory> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestShipmentHistory = RestOf<IShipmentHistory>;

export type NewRestShipmentHistory = RestOf<NewShipmentHistory>;

export type PartialUpdateRestShipmentHistory = RestOf<PartialUpdateShipmentHistory>;

export type EntityResponseType = HttpResponse<IShipmentHistory>;
export type EntityArrayResponseType = HttpResponse<IShipmentHistory[]>;

@Injectable({ providedIn: 'root' })
export class ShipmentHistoryService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipment-histories');

  create(shipmentHistory: NewShipmentHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipmentHistory);
    return this.http
      .post<RestShipmentHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(shipmentHistory: IShipmentHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipmentHistory);
    return this.http
      .put<RestShipmentHistory>(`${this.resourceUrl}/${this.getShipmentHistoryIdentifier(shipmentHistory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(shipmentHistory: PartialUpdateShipmentHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipmentHistory);
    return this.http
      .patch<RestShipmentHistory>(`${this.resourceUrl}/${this.getShipmentHistoryIdentifier(shipmentHistory)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestShipmentHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestShipmentHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipmentHistoryIdentifier(shipmentHistory: Pick<IShipmentHistory, 'id'>): number {
    return shipmentHistory.id;
  }

  compareShipmentHistory(o1: Pick<IShipmentHistory, 'id'> | null, o2: Pick<IShipmentHistory, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipmentHistoryIdentifier(o1) === this.getShipmentHistoryIdentifier(o2) : o1 === o2;
  }

  addShipmentHistoryToCollectionIfMissing<Type extends Pick<IShipmentHistory, 'id'>>(
    shipmentHistoryCollection: Type[],
    ...shipmentHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipmentHistories: Type[] = shipmentHistoriesToCheck.filter(isPresent);
    if (shipmentHistories.length > 0) {
      const shipmentHistoryCollectionIdentifiers = shipmentHistoryCollection.map(shipmentHistoryItem =>
        this.getShipmentHistoryIdentifier(shipmentHistoryItem),
      );
      const shipmentHistoriesToAdd = shipmentHistories.filter(shipmentHistoryItem => {
        const shipmentHistoryIdentifier = this.getShipmentHistoryIdentifier(shipmentHistoryItem);
        if (shipmentHistoryCollectionIdentifiers.includes(shipmentHistoryIdentifier)) {
          return false;
        }
        shipmentHistoryCollectionIdentifiers.push(shipmentHistoryIdentifier);
        return true;
      });
      return [...shipmentHistoriesToAdd, ...shipmentHistoryCollection];
    }
    return shipmentHistoryCollection;
  }

  protected convertDateFromClient<T extends IShipmentHistory | NewShipmentHistory | PartialUpdateShipmentHistory>(
    shipmentHistory: T,
  ): RestOf<T> {
    return {
      ...shipmentHistory,
      timestamp: shipmentHistory.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restShipmentHistory: RestShipmentHistory): IShipmentHistory {
    return {
      ...restShipmentHistory,
      timestamp: restShipmentHistory.timestamp ? dayjs(restShipmentHistory.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestShipmentHistory>): HttpResponse<IShipmentHistory> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestShipmentHistory[]>): HttpResponse<IShipmentHistory[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
