import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITruck } from '../truck.model';
import { TruckService } from '../service/truck.service';

export const truckResolve = (route: ActivatedRouteSnapshot): Observable<null | ITruck> => {
  const id = route.params['id'];
  if (id) {
    return inject(TruckService)
      .find(id)
      .pipe(
        mergeMap((truck: HttpResponse<ITruck>) => {
          if (truck.body) {
            return of(truck.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default truckResolve;
