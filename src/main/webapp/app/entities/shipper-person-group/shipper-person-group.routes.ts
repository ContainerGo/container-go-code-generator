import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipperPersonGroupComponent } from './list/shipper-person-group.component';
import { ShipperPersonGroupDetailComponent } from './detail/shipper-person-group-detail.component';
import { ShipperPersonGroupUpdateComponent } from './update/shipper-person-group-update.component';
import ShipperPersonGroupResolve from './route/shipper-person-group-routing-resolve.service';

const shipperPersonGroupRoute: Routes = [
  {
    path: '',
    component: ShipperPersonGroupComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipperPersonGroupDetailComponent,
    resolve: {
      shipperPersonGroup: ShipperPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipperPersonGroupUpdateComponent,
    resolve: {
      shipperPersonGroup: ShipperPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipperPersonGroupUpdateComponent,
    resolve: {
      shipperPersonGroup: ShipperPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default shipperPersonGroupRoute;
