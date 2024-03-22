import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProvice } from '../provice.model';
import { ProviceService } from '../service/provice.service';
import { ProviceFormService, ProviceFormGroup } from './provice-form.service';

@Component({
  standalone: true,
  selector: 'jhi-provice-update',
  templateUrl: './provice-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProviceUpdateComponent implements OnInit {
  isSaving = false;
  provice: IProvice | null = null;

  editForm: ProviceFormGroup = this.proviceFormService.createProviceFormGroup();

  constructor(
    protected proviceService: ProviceService,
    protected proviceFormService: ProviceFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ provice }) => {
      this.provice = provice;
      if (provice) {
        this.updateForm(provice);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const provice = this.proviceFormService.getProvice(this.editForm);
    if (provice.id !== null) {
      this.subscribeToSaveResponse(this.proviceService.update(provice));
    } else {
      this.subscribeToSaveResponse(this.proviceService.create(provice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvice>>): void {
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

  protected updateForm(provice: IProvice): void {
    this.provice = provice;
    this.proviceFormService.resetForm(this.editForm, provice);
  }
}
