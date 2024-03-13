import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IShipper } from 'app/entities/shipper/shipper.model';
import { ShipperService } from 'app/entities/shipper/service/shipper.service';
import { IShipperAccount } from '../shipper-account.model';
import { ShipperAccountService } from '../service/shipper-account.service';
import { ShipperAccountFormService, ShipperAccountFormGroup } from './shipper-account-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipper-account-update',
  templateUrl: './shipper-account-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipperAccountUpdateComponent implements OnInit {
  isSaving = false;
  shipperAccount: IShipperAccount | null = null;

  shippersSharedCollection: IShipper[] = [];

  editForm: ShipperAccountFormGroup = this.shipperAccountFormService.createShipperAccountFormGroup();

  constructor(
    protected shipperAccountService: ShipperAccountService,
    protected shipperAccountFormService: ShipperAccountFormService,
    protected shipperService: ShipperService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareShipper = (o1: IShipper | null, o2: IShipper | null): boolean => this.shipperService.compareShipper(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipperAccount }) => {
      this.shipperAccount = shipperAccount;
      if (shipperAccount) {
        this.updateForm(shipperAccount);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipperAccount = this.shipperAccountFormService.getShipperAccount(this.editForm);
    if (shipperAccount.id !== null) {
      this.subscribeToSaveResponse(this.shipperAccountService.update(shipperAccount));
    } else {
      this.subscribeToSaveResponse(this.shipperAccountService.create(shipperAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipperAccount>>): void {
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

  protected updateForm(shipperAccount: IShipperAccount): void {
    this.shipperAccount = shipperAccount;
    this.shipperAccountFormService.resetForm(this.editForm, shipperAccount);

    this.shippersSharedCollection = this.shipperService.addShipperToCollectionIfMissing<IShipper>(
      this.shippersSharedCollection,
      shipperAccount.shipper,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.shipperService
      .query()
      .pipe(map((res: HttpResponse<IShipper[]>) => res.body ?? []))
      .pipe(
        map((shippers: IShipper[]) =>
          this.shipperService.addShipperToCollectionIfMissing<IShipper>(shippers, this.shipperAccount?.shipper),
        ),
      )
      .subscribe((shippers: IShipper[]) => (this.shippersSharedCollection = shippers));
  }
}
