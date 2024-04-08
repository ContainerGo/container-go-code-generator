import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TruckComponent } from './list/truck.component';
import { TruckDetailComponent } from './detail/truck-detail.component';
import { TruckUpdateComponent } from './update/truck-update.component';
import TruckResolve from './route/truck-routing-resolve.service';

const truckRoute: Routes = [
  {
    path: '',
    component: TruckComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TruckDetailComponent,
    resolve: {
      truck: TruckResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TruckUpdateComponent,
    resolve: {
      truck: TruckResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TruckUpdateComponent,
    resolve: {
      truck: TruckResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default truckRoute;
