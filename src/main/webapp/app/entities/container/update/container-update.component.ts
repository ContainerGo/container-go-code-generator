import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProvice } from 'app/entities/provice/provice.model';
import { ProviceService } from 'app/entities/provice/service/provice.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { IWard } from 'app/entities/ward/ward.model';
import { WardService } from 'app/entities/ward/service/ward.service';
import { IContainerType } from 'app/entities/container-type/container-type.model';
import { ContainerTypeService } from 'app/entities/container-type/service/container-type.service';
import { IContainerStatus } from 'app/entities/container-status/container-status.model';
import { ContainerStatusService } from 'app/entities/container-status/service/container-status.service';
import { ITruckType } from 'app/entities/truck-type/truck-type.model';
import { TruckTypeService } from 'app/entities/truck-type/service/truck-type.service';
import { ContainerState } from 'app/entities/enumerations/container-state.model';
import { ContainerService } from '../service/container.service';
import { IContainer } from '../container.model';
import { ContainerFormService, ContainerFormGroup } from './container-form.service';

@Component({
  standalone: true,
  selector: 'jhi-container-update',
  templateUrl: './container-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContainerUpdateComponent implements OnInit {
  isSaving = false;
  container: IContainer | null = null;
  containerStateValues = Object.keys(ContainerState);

  provicesSharedCollection: IProvice[] = [];
  districtsSharedCollection: IDistrict[] = [];
  wardsSharedCollection: IWard[] = [];
  containerTypesSharedCollection: IContainerType[] = [];
  containerStatusesSharedCollection: IContainerStatus[] = [];
  truckTypesSharedCollection: ITruckType[] = [];

  editForm: ContainerFormGroup = this.containerFormService.createContainerFormGroup();

  constructor(
    protected containerService: ContainerService,
    protected containerFormService: ContainerFormService,
    protected proviceService: ProviceService,
    protected districtService: DistrictService,
    protected wardService: WardService,
    protected containerTypeService: ContainerTypeService,
    protected containerStatusService: ContainerStatusService,
    protected truckTypeService: TruckTypeService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareProvice = (o1: IProvice | null, o2: IProvice | null): boolean => this.proviceService.compareProvice(o1, o2);

  compareDistrict = (o1: IDistrict | null, o2: IDistrict | null): boolean => this.districtService.compareDistrict(o1, o2);

  compareWard = (o1: IWard | null, o2: IWard | null): boolean => this.wardService.compareWard(o1, o2);

  compareContainerType = (o1: IContainerType | null, o2: IContainerType | null): boolean =>
    this.containerTypeService.compareContainerType(o1, o2);

  compareContainerStatus = (o1: IContainerStatus | null, o2: IContainerStatus | null): boolean =>
    this.containerStatusService.compareContainerStatus(o1, o2);

  compareTruckType = (o1: ITruckType | null, o2: ITruckType | null): boolean => this.truckTypeService.compareTruckType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ container }) => {
      this.container = container;
      if (container) {
        this.updateForm(container);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const container = this.containerFormService.getContainer(this.editForm);
    if (container.id !== null) {
      this.subscribeToSaveResponse(this.containerService.update(container));
    } else {
      this.subscribeToSaveResponse(this.containerService.create(container));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainer>>): void {
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

  protected updateForm(container: IContainer): void {
    this.container = container;
    this.containerFormService.resetForm(this.editForm, container);

    this.provicesSharedCollection = this.proviceService.addProviceToCollectionIfMissing<IProvice>(
      this.provicesSharedCollection,
      container.dropoffProvice,
    );
    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing<IDistrict>(
      this.districtsSharedCollection,
      container.dropoffDistrict,
    );
    this.wardsSharedCollection = this.wardService.addWardToCollectionIfMissing<IWard>(this.wardsSharedCollection, container.dropoffWard);
    this.containerTypesSharedCollection = this.containerTypeService.addContainerTypeToCollectionIfMissing<IContainerType>(
      this.containerTypesSharedCollection,
      container.type,
    );
    this.containerStatusesSharedCollection = this.containerStatusService.addContainerStatusToCollectionIfMissing<IContainerStatus>(
      this.containerStatusesSharedCollection,
      container.status,
    );
    this.truckTypesSharedCollection = this.truckTypeService.addTruckTypeToCollectionIfMissing<ITruckType>(
      this.truckTypesSharedCollection,
      container.truckType,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proviceService
      .query()
      .pipe(map((res: HttpResponse<IProvice[]>) => res.body ?? []))
      .pipe(
        map((provices: IProvice[]) =>
          this.proviceService.addProviceToCollectionIfMissing<IProvice>(provices, this.container?.dropoffProvice),
        ),
      )
      .subscribe((provices: IProvice[]) => (this.provicesSharedCollection = provices));

    this.districtService
      .query()
      .pipe(map((res: HttpResponse<IDistrict[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistrict[]) =>
          this.districtService.addDistrictToCollectionIfMissing<IDistrict>(districts, this.container?.dropoffDistrict),
        ),
      )
      .subscribe((districts: IDistrict[]) => (this.districtsSharedCollection = districts));

    this.wardService
      .query()
      .pipe(map((res: HttpResponse<IWard[]>) => res.body ?? []))
      .pipe(map((wards: IWard[]) => this.wardService.addWardToCollectionIfMissing<IWard>(wards, this.container?.dropoffWard)))
      .subscribe((wards: IWard[]) => (this.wardsSharedCollection = wards));

    this.containerTypeService
      .query()
      .pipe(map((res: HttpResponse<IContainerType[]>) => res.body ?? []))
      .pipe(
        map((containerTypes: IContainerType[]) =>
          this.containerTypeService.addContainerTypeToCollectionIfMissing<IContainerType>(containerTypes, this.container?.type),
        ),
      )
      .subscribe((containerTypes: IContainerType[]) => (this.containerTypesSharedCollection = containerTypes));

    this.containerStatusService
      .query()
      .pipe(map((res: HttpResponse<IContainerStatus[]>) => res.body ?? []))
      .pipe(
        map((containerStatuses: IContainerStatus[]) =>
          this.containerStatusService.addContainerStatusToCollectionIfMissing<IContainerStatus>(containerStatuses, this.container?.status),
        ),
      )
      .subscribe((containerStatuses: IContainerStatus[]) => (this.containerStatusesSharedCollection = containerStatuses));

    this.truckTypeService
      .query()
      .pipe(map((res: HttpResponse<ITruckType[]>) => res.body ?? []))
      .pipe(
        map((truckTypes: ITruckType[]) =>
          this.truckTypeService.addTruckTypeToCollectionIfMissing<ITruckType>(truckTypes, this.container?.truckType),
        ),
      )
      .subscribe((truckTypes: ITruckType[]) => (this.truckTypesSharedCollection = truckTypes));
  }
}
