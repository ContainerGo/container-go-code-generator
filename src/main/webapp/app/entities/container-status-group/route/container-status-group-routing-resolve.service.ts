import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContainerStatusGroup } from '../container-status-group.model';
import { ContainerStatusGroupService } from '../service/container-status-group.service';

const containerStatusGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | IContainerStatusGroup> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContainerStatusGroupService)
      .find(id)
      .pipe(
        mergeMap((containerStatusGroup: HttpResponse<IContainerStatusGroup>) => {
          if (containerStatusGroup.body) {
            return of(containerStatusGroup.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default containerStatusGroupResolve;
