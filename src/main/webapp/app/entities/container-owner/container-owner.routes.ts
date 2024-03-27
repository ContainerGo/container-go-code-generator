import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContainerOwnerComponent } from './list/container-owner.component';
import { ContainerOwnerDetailComponent } from './detail/container-owner-detail.component';
import { ContainerOwnerUpdateComponent } from './update/container-owner-update.component';
import ContainerOwnerResolve from './route/container-owner-routing-resolve.service';

const containerOwnerRoute: Routes = [
  {
    path: '',
    component: ContainerOwnerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContainerOwnerDetailComponent,
    resolve: {
      containerOwner: ContainerOwnerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContainerOwnerUpdateComponent,
    resolve: {
      containerOwner: ContainerOwnerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContainerOwnerUpdateComponent,
    resolve: {
      containerOwner: ContainerOwnerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default containerOwnerRoute;
