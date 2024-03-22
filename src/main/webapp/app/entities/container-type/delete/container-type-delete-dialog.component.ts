import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContainerType } from '../container-type.model';
import { ContainerTypeService } from '../service/container-type.service';

@Component({
  standalone: true,
  templateUrl: './container-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContainerTypeDeleteDialogComponent {
  containerType?: IContainerType;

  constructor(
    protected containerTypeService: ContainerTypeService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.containerTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
