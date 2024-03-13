import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICarrier } from 'app/entities/carrier/carrier.model';
import { CarrierService } from 'app/entities/carrier/service/carrier.service';
import { ICarrierAccount } from '../carrier-account.model';
import { CarrierAccountService } from '../service/carrier-account.service';
import { CarrierAccountFormService, CarrierAccountFormGroup } from './carrier-account-form.service';

@Component({
  standalone: true,
  selector: 'jhi-carrier-account-update',
  templateUrl: './carrier-account-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CarrierAccountUpdateComponent implements OnInit {
  isSaving = false;
  carrierAccount: ICarrierAccount | null = null;

  carriersSharedCollection: ICarrier[] = [];

  editForm: CarrierAccountFormGroup = this.carrierAccountFormService.createCarrierAccountFormGroup();

  constructor(
    protected carrierAccountService: CarrierAccountService,
    protected carrierAccountFormService: CarrierAccountFormService,
    protected carrierService: CarrierService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCarrier = (o1: ICarrier | null, o2: ICarrier | null): boolean => this.carrierService.compareCarrier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carrierAccount }) => {
      this.carrierAccount = carrierAccount;
      if (carrierAccount) {
        this.updateForm(carrierAccount);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carrierAccount = this.carrierAccountFormService.getCarrierAccount(this.editForm);
    if (carrierAccount.id !== null) {
      this.subscribeToSaveResponse(this.carrierAccountService.update(carrierAccount));
    } else {
      this.subscribeToSaveResponse(this.carrierAccountService.create(carrierAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarrierAccount>>): void {
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

  protected updateForm(carrierAccount: ICarrierAccount): void {
    this.carrierAccount = carrierAccount;
    this.carrierAccountFormService.resetForm(this.editForm, carrierAccount);

    this.carriersSharedCollection = this.carrierService.addCarrierToCollectionIfMissing<ICarrier>(
      this.carriersSharedCollection,
      carrierAccount.carrier,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.carrierService
      .query()
      .pipe(map((res: HttpResponse<ICarrier[]>) => res.body ?? []))
      .pipe(
        map((carriers: ICarrier[]) =>
          this.carrierService.addCarrierToCollectionIfMissing<ICarrier>(carriers, this.carrierAccount?.carrier),
        ),
      )
      .subscribe((carriers: ICarrier[]) => (this.carriersSharedCollection = carriers));
  }
}
