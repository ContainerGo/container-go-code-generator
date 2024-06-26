import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICarrierAccount } from '../carrier-account.model';

@Component({
  standalone: true,
  selector: 'jhi-carrier-account-detail',
  templateUrl: './carrier-account-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CarrierAccountDetailComponent {
  carrierAccount = input<ICarrierAccount | null>(null);

  previousState(): void {
    window.history.back();
  }
}
