import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CarrierPersonComponent } from './list/carrier-person.component';
import { CarrierPersonDetailComponent } from './detail/carrier-person-detail.component';
import { CarrierPersonUpdateComponent } from './update/carrier-person-update.component';
import CarrierPersonResolve from './route/carrier-person-routing-resolve.service';

const carrierPersonRoute: Routes = [
  {
    path: '',
    component: CarrierPersonComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarrierPersonDetailComponent,
    resolve: {
      carrierPerson: CarrierPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarrierPersonUpdateComponent,
    resolve: {
      carrierPerson: CarrierPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarrierPersonUpdateComponent,
    resolve: {
      carrierPerson: CarrierPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default carrierPersonRoute;
