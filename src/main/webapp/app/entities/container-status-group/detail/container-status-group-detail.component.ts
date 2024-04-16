import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContainerStatusGroup } from '../container-status-group.model';

@Component({
  standalone: true,
  selector: 'jhi-container-status-group-detail',
  templateUrl: './container-status-group-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContainerStatusGroupDetailComponent {
  containerStatusGroup = input<IContainerStatusGroup | null>(null);

  previousState(): void {
    window.history.back();
  }
}
