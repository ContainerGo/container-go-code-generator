import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContainerOwner } from '../container-owner.model';
import { ContainerOwnerService } from '../service/container-owner.service';

@Component({
  standalone: true,
  templateUrl: './container-owner-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContainerOwnerDeleteDialogComponent {
  containerOwner?: IContainerOwner;

  protected containerOwnerService = inject(ContainerOwnerService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.containerOwnerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
