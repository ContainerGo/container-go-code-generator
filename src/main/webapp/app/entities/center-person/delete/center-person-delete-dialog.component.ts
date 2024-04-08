import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICenterPerson } from '../center-person.model';
import { CenterPersonService } from '../service/center-person.service';

@Component({
  standalone: true,
  templateUrl: './center-person-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CenterPersonDeleteDialogComponent {
  centerPerson?: ICenterPerson;

  protected centerPersonService = inject(CenterPersonService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.centerPersonService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
