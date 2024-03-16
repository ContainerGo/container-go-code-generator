import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICarrierAccount } from '../carrier-account.model';
import { CarrierAccountService } from '../service/carrier-account.service';

@Component({
  standalone: true,
  templateUrl: './carrier-account-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CarrierAccountDeleteDialogComponent {
  carrierAccount?: ICarrierAccount;

  constructor(
    protected carrierAccountService: CarrierAccountService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carrierAccountService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
