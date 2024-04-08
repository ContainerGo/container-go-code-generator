import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipperNotification, NewShipperNotification } from '../shipper-notification.model';

export type PartialUpdateShipperNotification = Partial<IShipperNotification> & Pick<IShipperNotification, 'id'>;

export type EntityResponseType = HttpResponse<IShipperNotification>;
export type EntityArrayResponseType = HttpResponse<IShipperNotification[]>;

@Injectable({ providedIn: 'root' })
export class ShipperNotificationService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipper-notifications');

  create(shipperNotification: NewShipperNotification): Observable<EntityResponseType> {
    return this.http.post<IShipperNotification>(this.resourceUrl, shipperNotification, { observe: 'response' });
  }

  update(shipperNotification: IShipperNotification): Observable<EntityResponseType> {
    return this.http.put<IShipperNotification>(
      `${this.resourceUrl}/${this.getShipperNotificationIdentifier(shipperNotification)}`,
      shipperNotification,
      { observe: 'response' },
    );
  }

  partialUpdate(shipperNotification: PartialUpdateShipperNotification): Observable<EntityResponseType> {
    return this.http.patch<IShipperNotification>(
      `${this.resourceUrl}/${this.getShipperNotificationIdentifier(shipperNotification)}`,
      shipperNotification,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IShipperNotification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipperNotification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipperNotificationIdentifier(shipperNotification: Pick<IShipperNotification, 'id'>): string {
    return shipperNotification.id;
  }

  compareShipperNotification(o1: Pick<IShipperNotification, 'id'> | null, o2: Pick<IShipperNotification, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipperNotificationIdentifier(o1) === this.getShipperNotificationIdentifier(o2) : o1 === o2;
  }

  addShipperNotificationToCollectionIfMissing<Type extends Pick<IShipperNotification, 'id'>>(
    shipperNotificationCollection: Type[],
    ...shipperNotificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipperNotifications: Type[] = shipperNotificationsToCheck.filter(isPresent);
    if (shipperNotifications.length > 0) {
      const shipperNotificationCollectionIdentifiers = shipperNotificationCollection.map(shipperNotificationItem =>
        this.getShipperNotificationIdentifier(shipperNotificationItem),
      );
      const shipperNotificationsToAdd = shipperNotifications.filter(shipperNotificationItem => {
        const shipperNotificationIdentifier = this.getShipperNotificationIdentifier(shipperNotificationItem);
        if (shipperNotificationCollectionIdentifiers.includes(shipperNotificationIdentifier)) {
          return false;
        }
        shipperNotificationCollectionIdentifiers.push(shipperNotificationIdentifier);
        return true;
      });
      return [...shipperNotificationsToAdd, ...shipperNotificationCollection];
    }
    return shipperNotificationCollection;
  }
}
