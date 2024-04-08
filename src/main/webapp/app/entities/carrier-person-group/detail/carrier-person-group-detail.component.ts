import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICarrierPersonGroup } from '../carrier-person-group.model';

@Component({
  standalone: true,
  selector: 'jhi-carrier-person-group-detail',
  templateUrl: './carrier-person-group-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CarrierPersonGroupDetailComponent {
  @Input() carrierPersonGroup: ICarrierPersonGroup | null = null;

  previousState(): void {
    window.history.back();
  }
}
