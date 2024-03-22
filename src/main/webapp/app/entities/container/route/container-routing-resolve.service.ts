import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContainer } from '../container.model';
import { ContainerService } from '../service/container.service';

export const containerResolve = (route: ActivatedRouteSnapshot): Observable<null | IContainer> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContainerService)
      .find(id)
      .pipe(
        mergeMap((container: HttpResponse<IContainer>) => {
          if (container.body) {
            return of(container.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default containerResolve;
