import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CenterPersonComponent } from './list/center-person.component';
import { CenterPersonDetailComponent } from './detail/center-person-detail.component';
import { CenterPersonUpdateComponent } from './update/center-person-update.component';
import CenterPersonResolve from './route/center-person-routing-resolve.service';

const centerPersonRoute: Routes = [
  {
    path: '',
    component: CenterPersonComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CenterPersonDetailComponent,
    resolve: {
      centerPerson: CenterPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CenterPersonUpdateComponent,
    resolve: {
      centerPerson: CenterPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CenterPersonUpdateComponent,
    resolve: {
      centerPerson: CenterPersonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centerPersonRoute;
