import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContainerStatus } from '../container-status.model';
import { ContainerStatusService } from '../service/container-status.service';

const containerStatusResolve = (route: ActivatedRouteSnapshot): Observable<null | IContainerStatus> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContainerStatusService)
      .find(id)
      .pipe(
        mergeMap((containerStatus: HttpResponse<IContainerStatus>) => {
          if (containerStatus.body) {
            return of(containerStatus.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default containerStatusResolve;
