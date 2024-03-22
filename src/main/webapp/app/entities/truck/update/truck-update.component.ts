import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITruckType } from 'app/entities/truck-type/truck-type.model';
import { TruckTypeService } from 'app/entities/truck-type/service/truck-type.service';
import { ICarrier } from 'app/entities/carrier/carrier.model';
import { CarrierService } from 'app/entities/carrier/service/carrier.service';
import { TruckStatus } from 'app/entities/enumerations/truck-status.model';
import { TruckService } from '../service/truck.service';
import { ITruck } from '../truck.model';
import { TruckFormService, TruckFormGroup } from './truck-form.service';

@Component({
  standalone: true,
  selector: 'jhi-truck-update',
  templateUrl: './truck-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TruckUpdateComponent implements OnInit {
  isSaving = false;
  truck: ITruck | null = null;
  truckStatusValues = Object.keys(TruckStatus);

  truckTypesSharedCollection: ITruckType[] = [];
  carriersSharedCollection: ICarrier[] = [];

  editForm: TruckFormGroup = this.truckFormService.createTruckFormGroup();

  constructor(
    protected truckService: TruckService,
    protected truckFormService: TruckFormService,
    protected truckTypeService: TruckTypeService,
    protected carrierService: CarrierService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareTruckType = (o1: ITruckType | null, o2: ITruckType | null): boolean => this.truckTypeService.compareTruckType(o1, o2);

  compareCarrier = (o1: ICarrier | null, o2: ICarrier | null): boolean => this.carrierService.compareCarrier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ truck }) => {
      this.truck = truck;
      if (truck) {
        this.updateForm(truck);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const truck = this.truckFormService.getTruck(this.editForm);
    if (truck.id !== null) {
      this.subscribeToSaveResponse(this.truckService.update(truck));
    } else {
      this.subscribeToSaveResponse(this.truckService.create(truck));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITruck>>): void {
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

  protected updateForm(truck: ITruck): void {
    this.truck = truck;
    this.truckFormService.resetForm(this.editForm, truck);

    this.truckTypesSharedCollection = this.truckTypeService.addTruckTypeToCollectionIfMissing<ITruckType>(
      this.truckTypesSharedCollection,
      truck.type,
    );
    this.carriersSharedCollection = this.carrierService.addCarrierToCollectionIfMissing<ICarrier>(
      this.carriersSharedCollection,
      truck.carrier,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.truckTypeService
      .query()
      .pipe(map((res: HttpResponse<ITruckType[]>) => res.body ?? []))
      .pipe(
        map((truckTypes: ITruckType[]) =>
          this.truckTypeService.addTruckTypeToCollectionIfMissing<ITruckType>(truckTypes, this.truck?.type),
        ),
      )
      .subscribe((truckTypes: ITruckType[]) => (this.truckTypesSharedCollection = truckTypes));

    this.carrierService
      .query()
      .pipe(map((res: HttpResponse<ICarrier[]>) => res.body ?? []))
      .pipe(map((carriers: ICarrier[]) => this.carrierService.addCarrierToCollectionIfMissing<ICarrier>(carriers, this.truck?.carrier)))
      .subscribe((carriers: ICarrier[]) => (this.carriersSharedCollection = carriers));
  }
}
