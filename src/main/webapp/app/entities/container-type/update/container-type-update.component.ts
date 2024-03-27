import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContainerType } from '../container-type.model';
import { ContainerTypeService } from '../service/container-type.service';
import { ContainerTypeFormService, ContainerTypeFormGroup } from './container-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-container-type-update',
  templateUrl: './container-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContainerTypeUpdateComponent implements OnInit {
  isSaving = false;
  containerType: IContainerType | null = null;

  protected containerTypeService = inject(ContainerTypeService);
  protected containerTypeFormService = inject(ContainerTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContainerTypeFormGroup = this.containerTypeFormService.createContainerTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ containerType }) => {
      this.containerType = containerType;
      if (containerType) {
        this.updateForm(containerType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const containerType = this.containerTypeFormService.getContainerType(this.editForm);
    if (containerType.id !== null) {
      this.subscribeToSaveResponse(this.containerTypeService.update(containerType));
    } else {
      this.subscribeToSaveResponse(this.containerTypeService.create(containerType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainerType>>): void {
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

  protected updateForm(containerType: IContainerType): void {
    this.containerType = containerType;
    this.containerTypeFormService.resetForm(this.editForm, containerType);
  }
}
