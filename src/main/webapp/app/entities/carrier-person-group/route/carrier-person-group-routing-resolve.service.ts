import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarrierPersonGroup } from '../carrier-person-group.model';
import { CarrierPersonGroupService } from '../service/carrier-person-group.service';

const carrierPersonGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | ICarrierPersonGroup> => {
  const id = route.params['id'];
  if (id) {
    return inject(CarrierPersonGroupService)
      .find(id)
      .pipe(
        mergeMap((carrierPersonGroup: HttpResponse<ICarrierPersonGroup>) => {
          if (carrierPersonGroup.body) {
            return of(carrierPersonGroup.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default carrierPersonGroupResolve;
