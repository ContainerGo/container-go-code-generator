import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarrierPerson } from '../carrier-person.model';
import { CarrierPersonService } from '../service/carrier-person.service';

export const carrierPersonResolve = (route: ActivatedRouteSnapshot): Observable<null | ICarrierPerson> => {
  const id = route.params['id'];
  if (id) {
    return inject(CarrierPersonService)
      .find(id)
      .pipe(
        mergeMap((carrierPerson: HttpResponse<ICarrierPerson>) => {
          if (carrierPerson.body) {
            return of(carrierPerson.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default carrierPersonResolve;
