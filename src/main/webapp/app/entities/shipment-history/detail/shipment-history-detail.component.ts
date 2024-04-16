import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IShipmentHistory } from '../shipment-history.model';

@Component({
  standalone: true,
  selector: 'jhi-shipment-history-detail',
  templateUrl: './shipment-history-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipmentHistoryDetailComponent {
  shipmentHistory = input<IShipmentHistory | null>(null);

  previousState(): void {
    window.history.back();
  }
}
