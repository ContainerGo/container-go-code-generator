import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICarrier } from 'app/entities/carrier/carrier.model';
import { CarrierService } from 'app/entities/carrier/service/carrier.service';
import { ICarrierPerson } from '../carrier-person.model';
import { CarrierPersonService } from '../service/carrier-person.service';
import { CarrierPersonFormService, CarrierPersonFormGroup } from './carrier-person-form.service';

@Component({
  standalone: true,
  selector: 'jhi-carrier-person-update',
  templateUrl: './carrier-person-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CarrierPersonUpdateComponent implements OnInit {
  isSaving = false;
  carrierPerson: ICarrierPerson | null = null;

  carriersSharedCollection: ICarrier[] = [];

  editForm: CarrierPersonFormGroup = this.carrierPersonFormService.createCarrierPersonFormGroup();

  constructor(
    protected carrierPersonService: CarrierPersonService,
    protected carrierPersonFormService: CarrierPersonFormService,
    protected carrierService: CarrierService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCarrier = (o1: ICarrier | null, o2: ICarrier | null): boolean => this.carrierService.compareCarrier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carrierPerson }) => {
      this.carrierPerson = carrierPerson;
      if (carrierPerson) {
        this.updateForm(carrierPerson);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carrierPerson = this.carrierPersonFormService.getCarrierPerson(this.editForm);
    if (carrierPerson.id !== null) {
      this.subscribeToSaveResponse(this.carrierPersonService.update(carrierPerson));
    } else {
      this.subscribeToSaveResponse(this.carrierPersonService.create(carrierPerson));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarrierPerson>>): void {
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

  protected updateForm(carrierPerson: ICarrierPerson): void {
    this.carrierPerson = carrierPerson;
    this.carrierPersonFormService.resetForm(this.editForm, carrierPerson);

    this.carriersSharedCollection = this.carrierService.addCarrierToCollectionIfMissing<ICarrier>(
      this.carriersSharedCollection,
      carrierPerson.carrier,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.carrierService
      .query()
      .pipe(map((res: HttpResponse<ICarrier[]>) => res.body ?? []))
      .pipe(
        map((carriers: ICarrier[]) => this.carrierService.addCarrierToCollectionIfMissing<ICarrier>(carriers, this.carrierPerson?.carrier)),
      )
      .subscribe((carriers: ICarrier[]) => (this.carriersSharedCollection = carriers));
  }
}
