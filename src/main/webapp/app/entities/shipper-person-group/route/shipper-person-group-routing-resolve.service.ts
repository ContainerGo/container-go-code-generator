import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipperPersonGroup } from '../shipper-person-group.model';
import { ShipperPersonGroupService } from '../service/shipper-person-group.service';

const shipperPersonGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | IShipperPersonGroup> => {
  const id = route.params['id'];
  if (id) {
    return inject(ShipperPersonGroupService)
      .find(id)
      .pipe(
        mergeMap((shipperPersonGroup: HttpResponse<IShipperPersonGroup>) => {
          if (shipperPersonGroup.body) {
            return of(shipperPersonGroup.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default shipperPersonGroupResolve;
