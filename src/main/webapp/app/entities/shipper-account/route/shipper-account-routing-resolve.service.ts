import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipperAccount } from '../shipper-account.model';
import { ShipperAccountService } from '../service/shipper-account.service';

const shipperAccountResolve = (route: ActivatedRouteSnapshot): Observable<null | IShipperAccount> => {
  const id = route.params['id'];
  if (id) {
    return inject(ShipperAccountService)
      .find(id)
      .pipe(
        mergeMap((shipperAccount: HttpResponse<IShipperAccount>) => {
          if (shipperAccount.body) {
            return of(shipperAccount.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default shipperAccountResolve;
