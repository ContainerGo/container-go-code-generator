import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITruck } from '../truck.model';
import { TruckService } from '../service/truck.service';
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

  editForm: TruckFormGroup = this.truckFormService.createTruckFormGroup();

  constructor(
    protected truckService: TruckService,
    protected truckFormService: TruckFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ truck }) => {
      this.truck = truck;
      if (truck) {
        this.updateForm(truck);
      }
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
  }
}
