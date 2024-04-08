import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipperComponent } from './list/shipper.component';
import { ShipperDetailComponent } from './detail/shipper-detail.component';
import { ShipperUpdateComponent } from './update/shipper-update.component';
import ShipperResolve from './route/shipper-routing-resolve.service';

const shipperRoute: Routes = [
  {
    path: '',
    component: ShipperComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipperDetailComponent,
    resolve: {
      shipper: ShipperResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipperUpdateComponent,
    resolve: {
      shipper: ShipperResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipperUpdateComponent,
    resolve: {
      shipper: ShipperResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default shipperRoute;
