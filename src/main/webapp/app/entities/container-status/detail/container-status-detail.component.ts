import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContainerStatus } from '../container-status.model';

@Component({
  standalone: true,
  selector: 'jhi-container-status-detail',
  templateUrl: './container-status-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContainerStatusDetailComponent {
  containerStatus = input<IContainerStatus | null>(null);

  previousState(): void {
    window.history.back();
  }
}
