import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICenterPerson } from '../center-person.model';

@Component({
  standalone: true,
  selector: 'jhi-center-person-detail',
  templateUrl: './center-person-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CenterPersonDetailComponent {
  centerPerson = input<ICenterPerson | null>(null);

  previousState(): void {
    window.history.back();
  }
}
