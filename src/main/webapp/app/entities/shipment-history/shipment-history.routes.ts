import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipmentHistoryComponent } from './list/shipment-history.component';
import { ShipmentHistoryDetailComponent } from './detail/shipment-history-detail.component';
import { ShipmentHistoryUpdateComponent } from './update/shipment-history-update.component';
import ShipmentHistoryResolve from './route/shipment-history-routing-resolve.service';

const shipmentHistoryRoute: Routes = [
  {
    path: '',
    component: ShipmentHistoryComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipmentHistoryDetailComponent,
    resolve: {
      shipmentHistory: ShipmentHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipmentHistoryUpdateComponent,
    resolve: {
      shipmentHistory: ShipmentHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipmentHistoryUpdateComponent,
    resolve: {
      shipmentHistory: ShipmentHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default shipmentHistoryRoute;
