import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContainerComponent } from './list/container.component';
import { ContainerDetailComponent } from './detail/container-detail.component';
import { ContainerUpdateComponent } from './update/container-update.component';
import ContainerResolve from './route/container-routing-resolve.service';

const containerRoute: Routes = [
  {
    path: '',
    component: ContainerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContainerDetailComponent,
    resolve: {
      container: ContainerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContainerUpdateComponent,
    resolve: {
      container: ContainerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContainerUpdateComponent,
    resolve: {
      container: ContainerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default containerRoute;
