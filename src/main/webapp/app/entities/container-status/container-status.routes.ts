import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContainerStatusComponent } from './list/container-status.component';
import { ContainerStatusDetailComponent } from './detail/container-status-detail.component';
import { ContainerStatusUpdateComponent } from './update/container-status-update.component';
import ContainerStatusResolve from './route/container-status-routing-resolve.service';

const containerStatusRoute: Routes = [
  {
    path: '',
    component: ContainerStatusComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContainerStatusDetailComponent,
    resolve: {
      containerStatus: ContainerStatusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContainerStatusUpdateComponent,
    resolve: {
      containerStatus: ContainerStatusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContainerStatusUpdateComponent,
    resolve: {
      containerStatus: ContainerStatusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default containerStatusRoute;
