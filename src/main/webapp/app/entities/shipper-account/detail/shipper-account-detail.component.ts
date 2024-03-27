import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IShipperAccount } from '../shipper-account.model';

@Component({
  standalone: true,
  selector: 'jhi-shipper-account-detail',
  templateUrl: './shipper-account-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipperAccountDetailComponent {
  @Input() shipperAccount: IShipperAccount | null = null;

  previousState(): void {
    window.history.back();
  }
}
