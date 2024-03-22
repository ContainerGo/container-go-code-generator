import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContainerStatusGroup } from '../container-status-group.model';
import { ContainerStatusGroupService } from '../service/container-status-group.service';
import { ContainerStatusGroupFormService, ContainerStatusGroupFormGroup } from './container-status-group-form.service';

@Component({
  standalone: true,
  selector: 'jhi-container-status-group-update',
  templateUrl: './container-status-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContainerStatusGroupUpdateComponent implements OnInit {
  isSaving = false;
  containerStatusGroup: IContainerStatusGroup | null = null;

  editForm: ContainerStatusGroupFormGroup = this.containerStatusGroupFormService.createContainerStatusGroupFormGroup();

  constructor(
    protected containerStatusGroupService: ContainerStatusGroupService,
    protected containerStatusGroupFormService: ContainerStatusGroupFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ containerStatusGroup }) => {
      this.containerStatusGroup = containerStatusGroup;
      if (containerStatusGroup) {
        this.updateForm(containerStatusGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const containerStatusGroup = this.containerStatusGroupFormService.getContainerStatusGroup(this.editForm);
    if (containerStatusGroup.id !== null) {
      this.subscribeToSaveResponse(this.containerStatusGroupService.update(containerStatusGroup));
    } else {
      this.subscribeToSaveResponse(this.containerStatusGroupService.create(containerStatusGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainerStatusGroup>>): void {
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

  protected updateForm(containerStatusGroup: IContainerStatusGroup): void {
    this.containerStatusGroup = containerStatusGroup;
    this.containerStatusGroupFormService.resetForm(this.editForm, containerStatusGroup);
  }
}
