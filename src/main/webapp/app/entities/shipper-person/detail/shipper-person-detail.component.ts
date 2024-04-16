import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IShipperPerson } from '../shipper-person.model';

@Component({
  standalone: true,
  selector: 'jhi-shipper-person-detail',
  templateUrl: './shipper-person-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipperPersonDetailComponent {
  shipperPerson = input<IShipperPerson | null>(null);

  previousState(): void {
    window.history.back();
  }
}
