import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CarrierAccountComponent } from './list/carrier-account.component';
import { CarrierAccountDetailComponent } from './detail/carrier-account-detail.component';
import { CarrierAccountUpdateComponent } from './update/carrier-account-update.component';
import CarrierAccountResolve from './route/carrier-account-routing-resolve.service';

const carrierAccountRoute: Routes = [
  {
    path: '',
    component: CarrierAccountComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarrierAccountDetailComponent,
    resolve: {
      carrierAccount: CarrierAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarrierAccountUpdateComponent,
    resolve: {
      carrierAccount: CarrierAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarrierAccountUpdateComponent,
    resolve: {
      carrierAccount: CarrierAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default carrierAccountRoute;
