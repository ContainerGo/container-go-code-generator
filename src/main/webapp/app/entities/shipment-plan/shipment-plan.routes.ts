import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipmentPlanComponent } from './list/shipment-plan.component';
import { ShipmentPlanDetailComponent } from './detail/shipment-plan-detail.component';
import { ShipmentPlanUpdateComponent } from './update/shipment-plan-update.component';
import ShipmentPlanResolve from './route/shipment-plan-routing-resolve.service';

const shipmentPlanRoute: Routes = [
  {
    path: '',
    component: ShipmentPlanComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipmentPlanDetailComponent,
    resolve: {
      shipmentPlan: ShipmentPlanResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipmentPlanUpdateComponent,
    resolve: {
      shipmentPlan: ShipmentPlanResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipmentPlanUpdateComponent,
    resolve: {
      shipmentPlan: ShipmentPlanResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default shipmentPlanRoute;
