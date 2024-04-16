import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipperAccountComponent } from './list/shipper-account.component';
import { ShipperAccountDetailComponent } from './detail/shipper-account-detail.component';
import { ShipperAccountUpdateComponent } from './update/shipper-account-update.component';
import ShipperAccountResolve from './route/shipper-account-routing-resolve.service';

const shipperAccountRoute: Routes = [
  {
    path: '',
    component: ShipperAccountComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipperAccountDetailComponent,
    resolve: {
      shipperAccount: ShipperAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipperAccountUpdateComponent,
    resolve: {
      shipperAccount: ShipperAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipperAccountUpdateComponent,
    resolve: {
      shipperAccount: ShipperAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default shipperAccountRoute;
