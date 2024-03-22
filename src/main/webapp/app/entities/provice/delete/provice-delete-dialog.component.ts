import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProvice } from '../provice.model';
import { ProviceService } from '../service/provice.service';

@Component({
  standalone: true,
  templateUrl: './provice-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProviceDeleteDialogComponent {
  provice?: IProvice;

  constructor(
    protected proviceService: ProviceService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proviceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
