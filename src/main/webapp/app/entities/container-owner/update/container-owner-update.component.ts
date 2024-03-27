import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContainerOwner } from '../container-owner.model';
import { ContainerOwnerService } from '../service/container-owner.service';
import { ContainerOwnerFormService, ContainerOwnerFormGroup } from './container-owner-form.service';

@Component({
  standalone: true,
  selector: 'jhi-container-owner-update',
  templateUrl: './container-owner-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContainerOwnerUpdateComponent implements OnInit {
  isSaving = false;
  containerOwner: IContainerOwner | null = null;

  protected containerOwnerService = inject(ContainerOwnerService);
  protected containerOwnerFormService = inject(ContainerOwnerFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContainerOwnerFormGroup = this.containerOwnerFormService.createContainerOwnerFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ containerOwner }) => {
      this.containerOwner = containerOwner;
      if (containerOwner) {
        this.updateForm(containerOwner);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const containerOwner = this.containerOwnerFormService.getContainerOwner(this.editForm);
    if (containerOwner.id !== null) {
      this.subscribeToSaveResponse(this.containerOwnerService.update(containerOwner));
    } else {
      this.subscribeToSaveResponse(this.containerOwnerService.create(containerOwner));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainerOwner>>): void {
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

  protected updateForm(containerOwner: IContainerOwner): void {
    this.containerOwner = containerOwner;
    this.containerOwnerFormService.resetForm(this.editForm, containerOwner);
  }
}
