import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IShipperPersonGroup } from '../shipper-person-group.model';
import { ShipperPersonGroupService } from '../service/shipper-person-group.service';

@Component({
  standalone: true,
  templateUrl: './shipper-person-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ShipperPersonGroupDeleteDialogComponent {
  shipperPersonGroup?: IShipperPersonGroup;

  protected shipperPersonGroupService = inject(ShipperPersonGroupService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.shipperPersonGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
