import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IShipperNotification } from '../shipper-notification.model';

@Component({
  standalone: true,
  selector: 'jhi-shipper-notification-detail',
  templateUrl: './shipper-notification-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipperNotificationDetailComponent {
  @Input() shipperNotification: IShipperNotification | null = null;

  previousState(): void {
    window.history.back();
  }
}
