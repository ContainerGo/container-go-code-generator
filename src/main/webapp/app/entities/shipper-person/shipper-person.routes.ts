import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipperPersonComponent } from './list/shipper-person.component';
import { ShipperPersonDetailComponent } from './detail/shipper-person-detail.component';
import { ShipperPersonUpdateComponent } from './update/shipper-person-update.component';
import ShipperPersonResolve from './route/shipper-person-routing-resolve.service';

const shipperPersonRoute: Routes = [
  {
    path: '',
    component: ShipperPersonComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipperPersonDetailComponent,
    resolve: {
      shipperPerson: ShipperPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipperPersonUpdateComponent,
    resolve: {
      shipperPerson: ShipperPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipperPersonUpdateComponent,
    resolve: {
      shipperPerson: ShipperPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default shipperPersonRoute;
