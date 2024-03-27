import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITruckType } from '../truck-type.model';
import { TruckTypeService } from '../service/truck-type.service';
import { TruckTypeFormService, TruckTypeFormGroup } from './truck-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-truck-type-update',
  templateUrl: './truck-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TruckTypeUpdateComponent implements OnInit {
  isSaving = false;
  truckType: ITruckType | null = null;

  protected truckTypeService = inject(TruckTypeService);
  protected truckTypeFormService = inject(TruckTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TruckTypeFormGroup = this.truckTypeFormService.createTruckTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ truckType }) => {
      this.truckType = truckType;
      if (truckType) {
        this.updateForm(truckType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const truckType = this.truckTypeFormService.getTruckType(this.editForm);
    if (truckType.id !== null) {
      this.subscribeToSaveResponse(this.truckTypeService.update(truckType));
    } else {
      this.subscribeToSaveResponse(this.truckTypeService.create(truckType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITruckType>>): void {
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

  protected updateForm(truckType: ITruckType): void {
    this.truckType = truckType;
    this.truckTypeFormService.resetForm(this.editForm, truckType);
  }
}
