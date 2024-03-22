import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContainerTypeComponent } from './list/container-type.component';
import { ContainerTypeDetailComponent } from './detail/container-type-detail.component';
import { ContainerTypeUpdateComponent } from './update/container-type-update.component';
import ContainerTypeResolve from './route/container-type-routing-resolve.service';

const containerTypeRoute: Routes = [
  {
    path: '',
    component: ContainerTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContainerTypeDetailComponent,
    resolve: {
      containerType: ContainerTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContainerTypeUpdateComponent,
    resolve: {
      containerType: ContainerTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContainerTypeUpdateComponent,
    resolve: {
      containerType: ContainerTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default containerTypeRoute;
