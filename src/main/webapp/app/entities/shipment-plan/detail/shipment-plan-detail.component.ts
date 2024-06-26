import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IShipmentPlan } from '../shipment-plan.model';

@Component({
  standalone: true,
  selector: 'jhi-shipment-plan-detail',
  templateUrl: './shipment-plan-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipmentPlanDetailComponent {
  shipmentPlan = input<IShipmentPlan | null>(null);

  previousState(): void {
    window.history.back();
  }
}
