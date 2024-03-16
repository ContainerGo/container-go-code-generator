import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IShipper } from 'app/entities/shipper/shipper.model';
import { ShipperService } from 'app/entities/shipper/service/shipper.service';
import { IShipperPerson } from '../shipper-person.model';
import { ShipperPersonService } from '../service/shipper-person.service';
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

  shippersSharedCollection: IShipper[] = [];

  editForm: ShipperPersonFormGroup = this.shipperPersonFormService.createShipperPersonFormGroup();

  constructor(
    protected shipperPersonService: ShipperPersonService,
    protected shipperPersonFormService: ShipperPersonFormService,
    protected shipperService: ShipperService,
    protected activatedRoute: ActivatedRoute,
  ) {}

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

    this.shippersSharedCollection = this.shipperService.addShipperToCollectionIfMissing<IShipper>(
      this.shippersSharedCollection,
      shipperPerson.shipper,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.shipperService
      .query()
      .pipe(map((res: HttpResponse<IShipper[]>) => res.body ?? []))
      .pipe(
        map((shippers: IShipper[]) => this.shipperService.addShipperToCollectionIfMissing<IShipper>(shippers, this.shipperPerson?.shipper)),
      )
      .subscribe((shippers: IShipper[]) => (this.shippersSharedCollection = shippers));
  }
}
