import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContainerType } from '../container-type.model';
import { ContainerTypeService } from '../service/container-type.service';

export const containerTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IContainerType> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContainerTypeService)
      .find(id)
      .pipe(
        mergeMap((containerType: HttpResponse<IContainerType>) => {
          if (containerType.body) {
            return of(containerType.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default containerTypeResolve;
