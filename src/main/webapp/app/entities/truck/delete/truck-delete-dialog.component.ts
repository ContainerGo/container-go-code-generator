import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITruck } from '../truck.model';
import { TruckService } from '../service/truck.service';

@Component({
  standalone: true,
  templateUrl: './truck-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TruckDeleteDialogComponent {
  truck?: ITruck;

  protected truckService = inject(TruckService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.truckService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
