import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICarrier } from '../carrier.model';
import { CarrierService } from '../service/carrier.service';

@Component({
  standalone: true,
  templateUrl: './carrier-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CarrierDeleteDialogComponent {
  carrier?: ICarrier;

  constructor(
    protected carrierService: CarrierService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carrierService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
