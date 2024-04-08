import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContainerStatusGroup } from '../container-status-group.model';
import { ContainerStatusGroupService } from '../service/container-status-group.service';

@Component({
  standalone: true,
  templateUrl: './container-status-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContainerStatusGroupDeleteDialogComponent {
  containerStatusGroup?: IContainerStatusGroup;

  protected containerStatusGroupService = inject(ContainerStatusGroupService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.containerStatusGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
