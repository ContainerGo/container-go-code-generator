import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICarrier } from '../carrier.model';
import { CarrierService } from '../service/carrier.service';
import { CarrierFormService, CarrierFormGroup } from './carrier-form.service';

@Component({
  standalone: true,
  selector: 'jhi-carrier-update',
  templateUrl: './carrier-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CarrierUpdateComponent implements OnInit {
  isSaving = false;
  carrier: ICarrier | null = null;

  protected carrierService = inject(CarrierService);
  protected carrierFormService = inject(CarrierFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CarrierFormGroup = this.carrierFormService.createCarrierFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carrier }) => {
      this.carrier = carrier;
      if (carrier) {
        this.updateForm(carrier);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carrier = this.carrierFormService.getCarrier(this.editForm);
    if (carrier.id !== null) {
      this.subscribeToSaveResponse(this.carrierService.update(carrier));
    } else {
      this.subscribeToSaveResponse(this.carrierService.create(carrier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarrier>>): void {
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

  protected updateForm(carrier: ICarrier): void {
    this.carrier = carrier;
    this.carrierFormService.resetForm(this.editForm, carrier);
  }
}
