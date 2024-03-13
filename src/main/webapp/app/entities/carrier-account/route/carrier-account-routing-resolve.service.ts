import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarrierAccount } from '../carrier-account.model';
import { CarrierAccountService } from '../service/carrier-account.service';

export const carrierAccountResolve = (route: ActivatedRouteSnapshot): Observable<null | ICarrierAccount> => {
  const id = route.params['id'];
  if (id) {
    return inject(CarrierAccountService)
      .find(id)
      .pipe(
        mergeMap((carrierAccount: HttpResponse<ICarrierAccount>) => {
          if (carrierAccount.body) {
            return of(carrierAccount.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default carrierAccountResolve;
