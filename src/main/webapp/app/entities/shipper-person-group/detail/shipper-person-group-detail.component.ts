import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IShipperPersonGroup } from '../shipper-person-group.model';

@Component({
  standalone: true,
  selector: 'jhi-shipper-person-group-detail',
  templateUrl: './shipper-person-group-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipperPersonGroupDetailComponent {
  shipperPersonGroup = input<IShipperPersonGroup | null>(null);

  previousState(): void {
    window.history.back();
  }
}
