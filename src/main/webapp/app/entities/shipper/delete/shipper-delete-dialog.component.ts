import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IShipper } from '../shipper.model';
import { ShipperService } from '../service/shipper.service';

@Component({
  standalone: true,
  templateUrl: './shipper-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ShipperDeleteDialogComponent {
  shipper?: IShipper;

  constructor(
    protected shipperService: ShipperService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shipperService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
