import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IShipperPersonGroup } from 'app/entities/shipper-person-group/shipper-person-group.model';
import { ShipperPersonGroupService } from 'app/entities/shipper-person-group/service/shipper-person-group.service';
import { IShipper } from 'app/entities/shipper/shipper.model';
import { ShipperService } from 'app/entities/shipper/service/shipper.service';
import { ShipperPersonService } from '../service/shipper-person.service';
import { IShipperPerson } from '../shipper-person.model';
import { ShipperPersonFormService, ShipperPersonFormGroup } from './shipper-person-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipper-person-update',
  templateUrl: './shipper-person-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipperPersonUpdateComponent implements OnInit {
  isSaving = false;
  shipperPerson: IShipperPerson | null = null;

  shipperPersonGroupsSharedCollection: IShipperPersonGroup[] = [];
  shippersSharedCollection: IShipper[] = [];

  protected shipperPersonService = inject(ShipperPersonService);
  protected shipperPersonFormService = inject(ShipperPersonFormService);
  protected shipperPersonGroupService = inject(ShipperPersonGroupService);
  protected shipperService = inject(ShipperService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ShipperPersonFormGroup = this.shipperPersonFormService.createShipperPersonFormGroup();

  compareShipperPersonGroup = (o1: IShipperPersonGroup | null, o2: IShipperPersonGroup | null): boolean =>
    this.shipperPersonGroupService.compareShipperPersonGroup(o1, o2);

  compareShipper = (o1: IShipper | null, o2: IShipper | null): boolean => this.shipperService.compareShipper(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipperPerson }) => {
      this.shipperPerson = shipperPerson;
      if (shipperPerson) {
        this.updateForm(shipperPerson);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipperPerson = this.shipperPersonFormService.getShipperPerson(this.editForm);
    if (shipperPerson.id !== null) {
      this.subscribeToSaveResponse(this.shipperPersonService.update(shipperPerson));
    } else {
      this.subscribeToSaveResponse(this.shipperPersonService.create(shipperPerson));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipperPerson>>): void {
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

  protected updateForm(shipperPerson: IShipperPerson): void {
    this.shipperPerson = shipperPerson;
    this.shipperPersonFormService.resetForm(this.editForm, shipperPerson);

    this.shipperPersonGroupsSharedCollection =
      this.shipperPersonGroupService.addShipperPersonGroupToCollectionIfMissing<IShipperPersonGroup>(
        this.shipperPersonGroupsSharedCollection,
        shipperPerson.group,
      );
    this.shippersSharedCollection = this.shipperService.addShipperToCollectionIfMissing<IShipper>(
      this.shippersSharedCollection,
      shipperPerson.shipper,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.shipperPersonGroupService
      .query()
      .pipe(map((res: HttpResponse<IShipperPersonGroup[]>) => res.body ?? []))
      .pipe(
        map((shipperPersonGroups: IShipperPersonGroup[]) =>
          this.shipperPersonGroupService.addShipperPersonGroupToCollectionIfMissing<IShipperPersonGroup>(
            shipperPersonGroups,
            this.shipperPerson?.group,
          ),
        ),
      )
      .subscribe((shipperPersonGroups: IShipperPersonGroup[]) => (this.shipperPersonGroupsSharedCollection = shipperPersonGroups));

    this.shipperService
      .query()
      .pipe(map((res: HttpResponse<IShipper[]>) => res.body ?? []))
      .pipe(
        map((shippers: IShipper[]) => this.shipperService.addShipperToCollectionIfMissing<IShipper>(shippers, this.shipperPerson?.shipper)),
      )
      .subscribe((shippers: IShipper[]) => (this.shippersSharedCollection = shippers));
  }
}
