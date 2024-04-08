import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITruckType } from '../truck-type.model';
import { TruckTypeService } from '../service/truck-type.service';

@Component({
  standalone: true,
  templateUrl: './truck-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TruckTypeDeleteDialogComponent {
  truckType?: ITruckType;

  protected truckTypeService = inject(TruckTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.truckTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
