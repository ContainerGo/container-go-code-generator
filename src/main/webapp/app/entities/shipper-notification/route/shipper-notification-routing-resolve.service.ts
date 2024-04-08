import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipperNotification } from '../shipper-notification.model';
import { ShipperNotificationService } from '../service/shipper-notification.service';

const shipperNotificationResolve = (route: ActivatedRouteSnapshot): Observable<null | IShipperNotification> => {
  const id = route.params['id'];
  if (id) {
    return inject(ShipperNotificationService)
      .find(id)
      .pipe(
        mergeMap((shipperNotification: HttpResponse<IShipperNotification>) => {
          if (shipperNotification.body) {
            return of(shipperNotification.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default shipperNotificationResolve;
