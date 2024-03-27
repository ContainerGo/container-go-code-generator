import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICarrierPerson } from '../carrier-person.model';

@Component({
  standalone: true,
  selector: 'jhi-carrier-person-detail',
  templateUrl: './carrier-person-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CarrierPersonDetailComponent {
  @Input() carrierPerson: ICarrierPerson | null = null;

  previousState(): void {
    window.history.back();
  }
}
