import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IWard } from '../ward.model';

@Component({
  standalone: true,
  selector: 'jhi-ward-detail',
  templateUrl: './ward-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class WardDetailComponent {
  @Input() ward: IWard | null = null;

  previousState(): void {
    window.history.back();
  }
}
