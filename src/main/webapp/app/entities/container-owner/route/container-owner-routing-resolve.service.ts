import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContainerOwner } from '../container-owner.model';
import { ContainerOwnerService } from '../service/container-owner.service';

const containerOwnerResolve = (route: ActivatedRouteSnapshot): Observable<null | IContainerOwner> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContainerOwnerService)
      .find(id)
      .pipe(
        mergeMap((containerOwner: HttpResponse<IContainerOwner>) => {
          if (containerOwner.body) {
            return of(containerOwner.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default containerOwnerResolve;
