import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICarrierPersonGroup } from '../carrier-person-group.model';
import { CarrierPersonGroupService } from '../service/carrier-person-group.service';

@Component({
  standalone: true,
  templateUrl: './carrier-person-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CarrierPersonGroupDeleteDialogComponent {
  carrierPersonGroup?: ICarrierPersonGroup;

  protected carrierPersonGroupService = inject(CarrierPersonGroupService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.carrierPersonGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
