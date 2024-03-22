import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { WardComponent } from './list/ward.component';
import { WardDetailComponent } from './detail/ward-detail.component';
import { WardUpdateComponent } from './update/ward-update.component';
import WardResolve from './route/ward-routing-resolve.service';

const wardRoute: Routes = [
  {
    path: '',
    component: WardComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WardDetailComponent,
    resolve: {
      ward: WardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WardUpdateComponent,
    resolve: {
      ward: WardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WardUpdateComponent,
    resolve: {
      ward: WardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default wardRoute;
