import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContainerStatus } from '../container-status.model';
import { ContainerStatusService } from '../service/container-status.service';

@Component({
  standalone: true,
  templateUrl: './container-status-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContainerStatusDeleteDialogComponent {
  containerStatus?: IContainerStatus;

  protected containerStatusService = inject(ContainerStatusService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.containerStatusService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
