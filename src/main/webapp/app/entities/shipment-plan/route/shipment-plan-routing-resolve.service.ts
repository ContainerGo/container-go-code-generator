import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipmentPlan } from '../shipment-plan.model';
import { ShipmentPlanService } from '../service/shipment-plan.service';

const shipmentPlanResolve = (route: ActivatedRouteSnapshot): Observable<null | IShipmentPlan> => {
  const id = route.params['id'];
  if (id) {
    return inject(ShipmentPlanService)
      .find(id)
      .pipe(
        mergeMap((shipmentPlan: HttpResponse<IShipmentPlan>) => {
          if (shipmentPlan.body) {
            return of(shipmentPlan.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default shipmentPlanResolve;
