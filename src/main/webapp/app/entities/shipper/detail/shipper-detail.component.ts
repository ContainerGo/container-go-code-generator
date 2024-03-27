import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IShipper } from '../shipper.model';

@Component({
  standalone: true,
  selector: 'jhi-shipper-detail',
  templateUrl: './shipper-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipperDetailComponent {
  @Input() shipper: IShipper | null = null;

  previousState(): void {
    window.history.back();
  }
}
