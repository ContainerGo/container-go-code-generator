import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IShipperPersonGroup } from '../shipper-person-group.model';
import { ShipperPersonGroupService } from '../service/shipper-person-group.service';
import { ShipperPersonGroupFormService, ShipperPersonGroupFormGroup } from './shipper-person-group-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipper-person-group-update',
  templateUrl: './shipper-person-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipperPersonGroupUpdateComponent implements OnInit {
  isSaving = false;
  shipperPersonGroup: IShipperPersonGroup | null = null;

  protected shipperPersonGroupService = inject(ShipperPersonGroupService);
  protected shipperPersonGroupFormService = inject(ShipperPersonGroupFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ShipperPersonGroupFormGroup = this.shipperPersonGroupFormService.createShipperPersonGroupFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipperPersonGroup }) => {
      this.shipperPersonGroup = shipperPersonGroup;
      if (shipperPersonGroup) {
        this.updateForm(shipperPersonGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipperPersonGroup = this.shipperPersonGroupFormService.getShipperPersonGroup(this.editForm);
    if (shipperPersonGroup.id !== null) {
      this.subscribeToSaveResponse(this.shipperPersonGroupService.update(shipperPersonGroup));
    } else {
      this.subscribeToSaveResponse(this.shipperPersonGroupService.create(shipperPersonGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipperPersonGroup>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(shipperPersonGroup: IShipperPersonGroup): void {
    this.shipperPersonGroup = shipperPersonGroup;
    this.shipperPersonGroupFormService.resetForm(this.editForm, shipperPersonGroup);
  }
}
