import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IShipmentPlan } from '../shipment-plan.model';
import { ShipmentPlanService } from '../service/shipment-plan.service';

@Component({
  standalone: true,
  templateUrl: './shipment-plan-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ShipmentPlanDeleteDialogComponent {
  shipmentPlan?: IShipmentPlan;

  protected shipmentPlanService = inject(ShipmentPlanService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.shipmentPlanService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
