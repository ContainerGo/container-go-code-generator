import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProviceComponent } from './list/provice.component';
import { ProviceDetailComponent } from './detail/provice-detail.component';
import { ProviceUpdateComponent } from './update/provice-update.component';
import ProviceResolve from './route/provice-routing-resolve.service';

const proviceRoute: Routes = [
  {
    path: '',
    component: ProviceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProviceDetailComponent,
    resolve: {
      provice: ProviceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProviceUpdateComponent,
    resolve: {
      provice: ProviceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProviceUpdateComponent,
    resolve: {
      provice: ProviceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default proviceRoute;
