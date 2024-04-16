import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProvice } from '../provice.model';

@Component({
  standalone: true,
  selector: 'jhi-provice-detail',
  templateUrl: './provice-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProviceDetailComponent {
  provice = input<IProvice | null>(null);

  previousState(): void {
    window.history.back();
  }
}
