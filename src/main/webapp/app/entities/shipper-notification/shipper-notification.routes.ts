import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipperNotificationComponent } from './list/shipper-notification.component';
import { ShipperNotificationDetailComponent } from './detail/shipper-notification-detail.component';
import { ShipperNotificationUpdateComponent } from './update/shipper-notification-update.component';
import ShipperNotificationResolve from './route/shipper-notification-routing-resolve.service';

const shipperNotificationRoute: Routes = [
  {
    path: '',
    component: ShipperNotificationComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipperNotificationDetailComponent,
    resolve: {
      shipperNotification: ShipperNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipperNotificationUpdateComponent,
    resolve: {
      shipperNotification: ShipperNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipperNotificationUpdateComponent,
    resolve: {
      shipperNotification: ShipperNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default shipperNotificationRoute;
