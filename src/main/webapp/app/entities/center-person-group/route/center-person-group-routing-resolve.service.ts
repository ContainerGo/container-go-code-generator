import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICenterPersonGroup } from '../center-person-group.model';
import { CenterPersonGroupService } from '../service/center-person-group.service';

const centerPersonGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | ICenterPersonGroup> => {
  const id = route.params['id'];
  if (id) {
    return inject(CenterPersonGroupService)
      .find(id)
      .pipe(
        mergeMap((centerPersonGroup: HttpResponse<ICenterPersonGroup>) => {
          if (centerPersonGroup.body) {
            return of(centerPersonGroup.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default centerPersonGroupResolve;
