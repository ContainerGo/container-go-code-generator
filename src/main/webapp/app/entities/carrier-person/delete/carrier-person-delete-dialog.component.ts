import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICarrierPerson } from '../carrier-person.model';
import { CarrierPersonService } from '../service/carrier-person.service';

@Component({
  standalone: true,
  templateUrl: './carrier-person-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CarrierPersonDeleteDialogComponent {
  carrierPerson?: ICarrierPerson;

  protected carrierPersonService = inject(CarrierPersonService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carrierPersonService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
