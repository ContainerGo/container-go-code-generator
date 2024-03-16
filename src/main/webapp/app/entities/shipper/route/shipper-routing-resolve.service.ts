import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipper } from '../shipper.model';
import { ShipperService } from '../service/shipper.service';

export const shipperResolve = (route: ActivatedRouteSnapshot): Observable<null | IShipper> => {
  const id = route.params['id'];
  if (id) {
    return inject(ShipperService)
      .find(id)
      .pipe(
        mergeMap((shipper: HttpResponse<IShipper>) => {
          if (shipper.body) {
            return of(shipper.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default shipperResolve;
