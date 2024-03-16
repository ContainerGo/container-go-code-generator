import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipperPerson } from '../shipper-person.model';
import { ShipperPersonService } from '../service/shipper-person.service';

export const shipperPersonResolve = (route: ActivatedRouteSnapshot): Observable<null | IShipperPerson> => {
  const id = route.params['id'];
  if (id) {
    return inject(ShipperPersonService)
      .find(id)
      .pipe(
        mergeMap((shipperPerson: HttpResponse<IShipperPerson>) => {
          if (shipperPerson.body) {
            return of(shipperPerson.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default shipperPersonResolve;
