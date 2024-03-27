import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipmentHistory } from '../shipment-history.model';
import { ShipmentHistoryService } from '../service/shipment-history.service';

const shipmentHistoryResolve = (route: ActivatedRouteSnapshot): Observable<null | IShipmentHistory> => {
  const id = route.params['id'];
  if (id) {
    return inject(ShipmentHistoryService)
      .find(id)
      .pipe(
        mergeMap((shipmentHistory: HttpResponse<IShipmentHistory>) => {
          if (shipmentHistory.body) {
            return of(shipmentHistory.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default shipmentHistoryResolve;
