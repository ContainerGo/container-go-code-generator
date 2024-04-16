import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IShipperAccount } from '../shipper-account.model';
import { ShipperAccountService } from '../service/shipper-account.service';

@Component({
  standalone: true,
  templateUrl: './shipper-account-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ShipperAccountDeleteDialogComponent {
  shipperAccount?: IShipperAccount;

  protected shipperAccountService = inject(ShipperAccountService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.shipperAccountService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
