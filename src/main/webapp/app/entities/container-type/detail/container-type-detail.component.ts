import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContainerType } from '../container-type.model';

@Component({
  standalone: true,
  selector: 'jhi-container-type-detail',
  templateUrl: './container-type-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContainerTypeDetailComponent {
  containerType = input<IContainerType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
