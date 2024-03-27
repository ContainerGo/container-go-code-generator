import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITruckType } from '../truck-type.model';
import { TruckTypeService } from '../service/truck-type.service';

const truckTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | ITruckType> => {
  const id = route.params['id'];
  if (id) {
    return inject(TruckTypeService)
      .find(id)
      .pipe(
        mergeMap((truckType: HttpResponse<ITruckType>) => {
          if (truckType.body) {
            return of(truckType.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default truckTypeResolve;
