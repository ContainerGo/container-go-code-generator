import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IWard } from '../ward.model';
import { WardService } from '../service/ward.service';

@Component({
  standalone: true,
  templateUrl: './ward-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class WardDeleteDialogComponent {
  ward?: IWard;

  protected wardService = inject(WardService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.wardService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
