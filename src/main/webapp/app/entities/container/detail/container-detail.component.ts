import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContainer } from '../container.model';

@Component({
  standalone: true,
  selector: 'jhi-container-detail',
  templateUrl: './container-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContainerDetailComponent {
  container = input<IContainer | null>(null);

  previousState(): void {
    window.history.back();
  }
}
