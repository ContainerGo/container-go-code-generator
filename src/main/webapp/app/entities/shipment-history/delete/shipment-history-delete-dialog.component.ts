import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IShipmentHistory } from '../shipment-history.model';
import { ShipmentHistoryService } from '../service/shipment-history.service';

@Component({
  standalone: true,
  templateUrl: './shipment-history-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ShipmentHistoryDeleteDialogComponent {
  shipmentHistory?: IShipmentHistory;

  protected shipmentHistoryService = inject(ShipmentHistoryService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.shipmentHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
