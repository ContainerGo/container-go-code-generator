import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContainerStatusGroupComponent } from './list/container-status-group.component';
import { ContainerStatusGroupDetailComponent } from './detail/container-status-group-detail.component';
import { ContainerStatusGroupUpdateComponent } from './update/container-status-group-update.component';
import ContainerStatusGroupResolve from './route/container-status-group-routing-resolve.service';

const containerStatusGroupRoute: Routes = [
  {
    path: '',
    component: ContainerStatusGroupComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContainerStatusGroupDetailComponent,
    resolve: {
      containerStatusGroup: ContainerStatusGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContainerStatusGroupUpdateComponent,
    resolve: {
      containerStatusGroup: ContainerStatusGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContainerStatusGroupUpdateComponent,
    resolve: {
      containerStatusGroup: ContainerStatusGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default containerStatusGroupRoute;
