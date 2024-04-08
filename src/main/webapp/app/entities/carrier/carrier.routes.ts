import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CarrierComponent } from './list/carrier.component';
import { CarrierDetailComponent } from './detail/carrier-detail.component';
import { CarrierUpdateComponent } from './update/carrier-update.component';
import CarrierResolve from './route/carrier-routing-resolve.service';

const carrierRoute: Routes = [
  {
    path: '',
    component: CarrierComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarrierDetailComponent,
    resolve: {
      carrier: CarrierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarrierUpdateComponent,
    resolve: {
      carrier: CarrierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarrierUpdateComponent,
    resolve: {
      carrier: CarrierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default carrierRoute;
