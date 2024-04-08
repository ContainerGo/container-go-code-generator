import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CarrierPersonGroupComponent } from './list/carrier-person-group.component';
import { CarrierPersonGroupDetailComponent } from './detail/carrier-person-group-detail.component';
import { CarrierPersonGroupUpdateComponent } from './update/carrier-person-group-update.component';
import CarrierPersonGroupResolve from './route/carrier-person-group-routing-resolve.service';

const carrierPersonGroupRoute: Routes = [
  {
    path: '',
    component: CarrierPersonGroupComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarrierPersonGroupDetailComponent,
    resolve: {
      carrierPersonGroup: CarrierPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarrierPersonGroupUpdateComponent,
    resolve: {
      carrierPersonGroup: CarrierPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarrierPersonGroupUpdateComponent,
    resolve: {
      carrierPersonGroup: CarrierPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default carrierPersonGroupRoute;
