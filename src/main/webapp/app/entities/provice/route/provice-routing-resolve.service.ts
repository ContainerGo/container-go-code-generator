import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProvice } from '../provice.model';
import { ProviceService } from '../service/provice.service';

export const proviceResolve = (route: ActivatedRouteSnapshot): Observable<null | IProvice> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProviceService)
      .find(id)
      .pipe(
        mergeMap((provice: HttpResponse<IProvice>) => {
          if (provice.body) {
            return of(provice.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default proviceResolve;
