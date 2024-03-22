import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TruckTypeComponent } from './list/truck-type.component';
import { TruckTypeDetailComponent } from './detail/truck-type-detail.component';
import { TruckTypeUpdateComponent } from './update/truck-type-update.component';
import TruckTypeResolve from './route/truck-type-routing-resolve.service';

const truckTypeRoute: Routes = [
  {
    path: '',
    component: TruckTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TruckTypeDetailComponent,
    resolve: {
      truckType: TruckTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TruckTypeUpdateComponent,
    resolve: {
      truckType: TruckTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TruckTypeUpdateComponent,
    resolve: {
      truckType: TruckTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default truckTypeRoute;
