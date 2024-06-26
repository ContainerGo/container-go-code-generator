import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITruck } from '../truck.model';

@Component({
  standalone: true,
  selector: 'jhi-truck-detail',
  templateUrl: './truck-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TruckDetailComponent {
  truck = input<ITruck | null>(null);

  previousState(): void {
    window.history.back();
  }
}
