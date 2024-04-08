import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContainer } from 'app/entities/container/container.model';
import { ContainerService } from 'app/entities/container/service/container.service';
import { IShipmentPlan } from '../shipment-plan.model';
import { ShipmentPlanService } from '../service/shipment-plan.service';
import { ShipmentPlanFormService, ShipmentPlanFormGroup } from './shipment-plan-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipment-plan-update',
  templateUrl: './shipment-plan-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipmentPlanUpdateComponent implements OnInit {
  isSaving = false;
  shipmentPlan: IShipmentPlan | null = null;

  containersSharedCollection: IContainer[] = [];

  protected shipmentPlanService = inject(ShipmentPlanService);
  protected shipmentPlanFormService = inject(ShipmentPlanFormService);
  protected containerService = inject(ContainerService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ShipmentPlanFormGroup = this.shipmentPlanFormService.createShipmentPlanFormGroup();

  compareContainer = (o1: IContainer | null, o2: IContainer | null): boolean => this.containerService.compareContainer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipmentPlan }) => {
      this.shipmentPlan = shipmentPlan;
      if (shipmentPlan) {
        this.updateForm(shipmentPlan);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipmentPlan = this.shipmentPlanFormService.getShipmentPlan(this.editForm);
    if (shipmentPlan.id !== null) {
      this.subscribeToSaveResponse(this.shipmentPlanService.update(shipmentPlan));
    } else {
      this.subscribeToSaveResponse(this.shipmentPlanService.create(shipmentPlan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipmentPlan>>): void {
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

  protected updateForm(shipmentPlan: IShipmentPlan): void {
    this.shipmentPlan = shipmentPlan;
    this.shipmentPlanFormService.resetForm(this.editForm, shipmentPlan);

    this.containersSharedCollection = this.containerService.addContainerToCollectionIfMissing<IContainer>(
      this.containersSharedCollection,
      shipmentPlan.container,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.containerService
      .query()
      .pipe(map((res: HttpResponse<IContainer[]>) => res.body ?? []))
      .pipe(
        map((containers: IContainer[]) =>
          this.containerService.addContainerToCollectionIfMissing<IContainer>(containers, this.shipmentPlan?.container),
        ),
      )
      .subscribe((containers: IContainer[]) => (this.containersSharedCollection = containers));
  }
}
