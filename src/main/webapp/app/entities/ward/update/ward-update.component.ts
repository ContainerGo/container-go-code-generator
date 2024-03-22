import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWard } from '../ward.model';
import { WardService } from '../service/ward.service';
import { WardFormService, WardFormGroup } from './ward-form.service';

@Component({
  standalone: true,
  selector: 'jhi-ward-update',
  templateUrl: './ward-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WardUpdateComponent implements OnInit {
  isSaving = false;
  ward: IWard | null = null;

  editForm: WardFormGroup = this.wardFormService.createWardFormGroup();

  constructor(
    protected wardService: WardService,
    protected wardFormService: WardFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ward }) => {
      this.ward = ward;
      if (ward) {
        this.updateForm(ward);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ward = this.wardFormService.getWard(this.editForm);
    if (ward.id !== null) {
      this.subscribeToSaveResponse(this.wardService.update(ward));
    } else {
      this.subscribeToSaveResponse(this.wardService.create(ward));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWard>>): void {
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

  protected updateForm(ward: IWard): void {
    this.ward = ward;
    this.wardFormService.resetForm(this.editForm, ward);
  }
}
