import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContainer } from 'app/entities/container/container.model';
import { ContainerService } from 'app/entities/container/service/container.service';
import { IShipmentHistory } from '../shipment-history.model';
import { ShipmentHistoryService } from '../service/shipment-history.service';
import { ShipmentHistoryFormService, ShipmentHistoryFormGroup } from './shipment-history-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipment-history-update',
  templateUrl: './shipment-history-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipmentHistoryUpdateComponent implements OnInit {
  isSaving = false;
  shipmentHistory: IShipmentHistory | null = null;

  containersSharedCollection: IContainer[] = [];

  protected shipmentHistoryService = inject(ShipmentHistoryService);
  protected shipmentHistoryFormService = inject(ShipmentHistoryFormService);
  protected containerService = inject(ContainerService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ShipmentHistoryFormGroup = this.shipmentHistoryFormService.createShipmentHistoryFormGroup();

  compareContainer = (o1: IContainer | null, o2: IContainer | null): boolean => this.containerService.compareContainer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipmentHistory }) => {
      this.shipmentHistory = shipmentHistory;
      if (shipmentHistory) {
        this.updateForm(shipmentHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipmentHistory = this.shipmentHistoryFormService.getShipmentHistory(this.editForm);
    if (shipmentHistory.id !== null) {
      this.subscribeToSaveResponse(this.shipmentHistoryService.update(shipmentHistory));
    } else {
      this.subscribeToSaveResponse(this.shipmentHistoryService.create(shipmentHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipmentHistory>>): void {
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

  protected updateForm(shipmentHistory: IShipmentHistory): void {
    this.shipmentHistory = shipmentHistory;
    this.shipmentHistoryFormService.resetForm(this.editForm, shipmentHistory);

    this.containersSharedCollection = this.containerService.addContainerToCollectionIfMissing<IContainer>(
      this.containersSharedCollection,
      shipmentHistory.container,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.containerService
      .query()
      .pipe(map((res: HttpResponse<IContainer[]>) => res.body ?? []))
      .pipe(
        map((containers: IContainer[]) =>
          this.containerService.addContainerToCollectionIfMissing<IContainer>(containers, this.shipmentHistory?.container),
        ),
      )
      .subscribe((containers: IContainer[]) => (this.containersSharedCollection = containers));
  }
}
