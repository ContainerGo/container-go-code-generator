import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CenterPersonGroupComponent } from './list/center-person-group.component';
import { CenterPersonGroupDetailComponent } from './detail/center-person-group-detail.component';
import { CenterPersonGroupUpdateComponent } from './update/center-person-group-update.component';
import CenterPersonGroupResolve from './route/center-person-group-routing-resolve.service';

const centerPersonGroupRoute: Routes = [
  {
    path: '',
    component: CenterPersonGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CenterPersonGroupDetailComponent,
    resolve: {
      centerPersonGroup: CenterPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CenterPersonGroupUpdateComponent,
    resolve: {
      centerPersonGroup: CenterPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CenterPersonGroupUpdateComponent,
    resolve: {
      centerPersonGroup: CenterPersonGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centerPersonGroupRoute;
