import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarrier } from '../carrier.model';
import { CarrierService } from '../service/carrier.service';

const carrierResolve = (route: ActivatedRouteSnapshot): Observable<null | ICarrier> => {
  const id = route.params['id'];
  if (id) {
    return inject(CarrierService)
      .find(id)
      .pipe(
        mergeMap((carrier: HttpResponse<ICarrier>) => {
          if (carrier.body) {
            return of(carrier.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default carrierResolve;
