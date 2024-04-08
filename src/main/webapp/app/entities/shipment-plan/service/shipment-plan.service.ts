import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipmentPlan, NewShipmentPlan } from '../shipment-plan.model';

export type PartialUpdateShipmentPlan = Partial<IShipmentPlan> & Pick<IShipmentPlan, 'id'>;

type RestOf<T extends IShipmentPlan | NewShipmentPlan> = Omit<
  T,
  'estimatedPickupFromDate' | 'estimatedPickupUntilDate' | 'estimatedDropoffFromDate' | 'estimatedDropoffUntilDate'
> & {
  estimatedPickupFromDate?: string | null;
  estimatedPickupUntilDate?: string | null;
  estimatedDropoffFromDate?: string | null;
  estimatedDropoffUntilDate?: string | null;
};

export type RestShipmentPlan = RestOf<IShipmentPlan>;

export type NewRestShipmentPlan = RestOf<NewShipmentPlan>;

export type PartialUpdateRestShipmentPlan = RestOf<PartialUpdateShipmentPlan>;

export type EntityResponseType = HttpResponse<IShipmentPlan>;
export type EntityArrayResponseType = HttpResponse<IShipmentPlan[]>;

@Injectable({ providedIn: 'root' })
export class ShipmentPlanService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipment-plans');

  create(shipmentPlan: NewShipmentPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipmentPlan);
    return this.http
      .post<RestShipmentPlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(shipmentPlan: IShipmentPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipmentPlan);
    return this.http
      .put<RestShipmentPlan>(`${this.resourceUrl}/${this.getShipmentPlanIdentifier(shipmentPlan)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(shipmentPlan: PartialUpdateShipmentPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shipmentPlan);
    return this.http
      .patch<RestShipmentPlan>(`${this.resourceUrl}/${this.getShipmentPlanIdentifier(shipmentPlan)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestShipmentPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestShipmentPlan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipmentPlanIdentifier(shipmentPlan: Pick<IShipmentPlan, 'id'>): string {
    return shipmentPlan.id;
  }

  compareShipmentPlan(o1: Pick<IShipmentPlan, 'id'> | null, o2: Pick<IShipmentPlan, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipmentPlanIdentifier(o1) === this.getShipmentPlanIdentifier(o2) : o1 === o2;
  }

  addShipmentPlanToCollectionIfMissing<Type extends Pick<IShipmentPlan, 'id'>>(
    shipmentPlanCollection: Type[],
    ...shipmentPlansToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipmentPlans: Type[] = shipmentPlansToCheck.filter(isPresent);
    if (shipmentPlans.length > 0) {
      const shipmentPlanCollectionIdentifiers = shipmentPlanCollection.map(shipmentPlanItem =>
        this.getShipmentPlanIdentifier(shipmentPlanItem),
      );
      const shipmentPlansToAdd = shipmentPlans.filter(shipmentPlanItem => {
        const shipmentPlanIdentifier = this.getShipmentPlanIdentifier(shipmentPlanItem);
        if (shipmentPlanCollectionIdentifiers.includes(shipmentPlanIdentifier)) {
          return false;
        }
        shipmentPlanCollectionIdentifiers.push(shipmentPlanIdentifier);
        return true;
      });
      return [...shipmentPlansToAdd, ...shipmentPlanCollection];
    }
    return shipmentPlanCollection;
  }

  protected convertDateFromClient<T extends IShipmentPlan | NewShipmentPlan | PartialUpdateShipmentPlan>(shipmentPlan: T): RestOf<T> {
    return {
      ...shipmentPlan,
      estimatedPickupFromDate: shipmentPlan.estimatedPickupFromDate?.toJSON() ?? null,
      estimatedPickupUntilDate: shipmentPlan.estimatedPickupUntilDate?.toJSON() ?? null,
      estimatedDropoffFromDate: shipmentPlan.estimatedDropoffFromDate?.toJSON() ?? null,
      estimatedDropoffUntilDate: shipmentPlan.estimatedDropoffUntilDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restShipmentPlan: RestShipmentPlan): IShipmentPlan {
    return {
      ...restShipmentPlan,
      estimatedPickupFromDate: restShipmentPlan.estimatedPickupFromDate ? dayjs(restShipmentPlan.estimatedPickupFromDate) : undefined,
      estimatedPickupUntilDate: restShipmentPlan.estimatedPickupUntilDate ? dayjs(restShipmentPlan.estimatedPickupUntilDate) : undefined,
      estimatedDropoffFromDate: restShipmentPlan.estimatedDropoffFromDate ? dayjs(restShipmentPlan.estimatedDropoffFromDate) : undefined,
      estimatedDropoffUntilDate: restShipmentPlan.estimatedDropoffUntilDate ? dayjs(restShipmentPlan.estimatedDropoffUntilDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestShipmentPlan>): HttpResponse<IShipmentPlan> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestShipmentPlan[]>): HttpResponse<IShipmentPlan[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
