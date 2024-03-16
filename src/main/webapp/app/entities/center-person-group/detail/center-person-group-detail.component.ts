import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICenterPersonGroup } from '../center-person-group.model';

@Component({
  standalone: true,
  selector: 'jhi-center-person-group-detail',
  templateUrl: './center-person-group-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CenterPersonGroupDetailComponent {
  @Input() centerPersonGroup: ICenterPersonGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
