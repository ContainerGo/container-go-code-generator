import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICarrierPersonGroup } from '../carrier-person-group.model';
import { CarrierPersonGroupService } from '../service/carrier-person-group.service';
import { CarrierPersonGroupFormService, CarrierPersonGroupFormGroup } from './carrier-person-group-form.service';

@Component({
  standalone: true,
  selector: 'jhi-carrier-person-group-update',
  templateUrl: './carrier-person-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CarrierPersonGroupUpdateComponent implements OnInit {
  isSaving = false;
  carrierPersonGroup: ICarrierPersonGroup | null = null;

  protected carrierPersonGroupService = inject(CarrierPersonGroupService);
  protected carrierPersonGroupFormService = inject(CarrierPersonGroupFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CarrierPersonGroupFormGroup = this.carrierPersonGroupFormService.createCarrierPersonGroupFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carrierPersonGroup }) => {
      this.carrierPersonGroup = carrierPersonGroup;
      if (carrierPersonGroup) {
        this.updateForm(carrierPersonGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carrierPersonGroup = this.carrierPersonGroupFormService.getCarrierPersonGroup(this.editForm);
    if (carrierPersonGroup.id !== null) {
      this.subscribeToSaveResponse(this.carrierPersonGroupService.update(carrierPersonGroup));
    } else {
      this.subscribeToSaveResponse(this.carrierPersonGroupService.create(carrierPersonGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarrierPersonGroup>>): void {
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

  protected updateForm(carrierPersonGroup: ICarrierPersonGroup): void {
    this.carrierPersonGroup = carrierPersonGroup;
    this.carrierPersonGroupFormService.resetForm(this.editForm, carrierPersonGroup);
  }
}
