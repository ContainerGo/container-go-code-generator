import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICenterPersonGroup } from '../center-person-group.model';
import { CenterPersonGroupService } from '../service/center-person-group.service';

@Component({
  standalone: true,
  templateUrl: './center-person-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CenterPersonGroupDeleteDialogComponent {
  centerPersonGroup?: ICenterPersonGroup;

  protected centerPersonGroupService = inject(CenterPersonGroupService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.centerPersonGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
