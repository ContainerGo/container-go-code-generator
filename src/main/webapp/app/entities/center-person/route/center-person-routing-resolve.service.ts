import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICenterPerson } from '../center-person.model';
import { CenterPersonService } from '../service/center-person.service';

export const centerPersonResolve = (route: ActivatedRouteSnapshot): Observable<null | ICenterPerson> => {
  const id = route.params['id'];
  if (id) {
    return inject(CenterPersonService)
      .find(id)
      .pipe(
        mergeMap((centerPerson: HttpResponse<ICenterPerson>) => {
          if (centerPerson.body) {
            return of(centerPerson.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default centerPersonResolve;
