import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IShipperNotification } from '../shipper-notification.model';
import { ShipperNotificationService } from '../service/shipper-notification.service';

@Component({
  standalone: true,
  templateUrl: './shipper-notification-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ShipperNotificationDeleteDialogComponent {
  shipperNotification?: IShipperNotification;

  protected shipperNotificationService = inject(ShipperNotificationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.shipperNotificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
