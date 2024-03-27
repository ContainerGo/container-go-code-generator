import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IContainerOwner } from '../container-owner.model';

@Component({
  standalone: true,
  selector: 'jhi-container-owner-detail',
  templateUrl: './container-owner-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContainerOwnerDetailComponent {
  @Input() containerOwner: IContainerOwner | null = null;

  previousState(): void {
    window.history.back();
  }
}
