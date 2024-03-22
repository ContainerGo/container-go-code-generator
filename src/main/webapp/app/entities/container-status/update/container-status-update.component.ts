import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContainerStatusGroup } from 'app/entities/container-status-group/container-status-group.model';
import { ContainerStatusGroupService } from 'app/entities/container-status-group/service/container-status-group.service';
import { IContainerStatus } from '../container-status.model';
import { ContainerStatusService } from '../service/container-status.service';
import { ContainerStatusFormService, ContainerStatusFormGroup } from './container-status-form.service';

@Component({
  standalone: true,
  selector: 'jhi-container-status-update',
  templateUrl: './container-status-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContainerStatusUpdateComponent implements OnInit {
  isSaving = false;
  containerStatus: IContainerStatus | null = null;

  containerStatusGroupsSharedCollection: IContainerStatusGroup[] = [];

  editForm: ContainerStatusFormGroup = this.containerStatusFormService.createContainerStatusFormGroup();

  constructor(
    protected containerStatusService: ContainerStatusService,
    protected containerStatusFormService: ContainerStatusFormService,
    protected containerStatusGroupService: ContainerStatusGroupService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareContainerStatusGroup = (o1: IContainerStatusGroup | null, o2: IContainerStatusGroup | null): boolean =>
    this.containerStatusGroupService.compareContainerStatusGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ containerStatus }) => {
      this.containerStatus = containerStatus;
      if (containerStatus) {
        this.updateForm(containerStatus);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const containerStatus = this.containerStatusFormService.getContainerStatus(this.editForm);
    if (containerStatus.id !== null) {
      this.subscribeToSaveResponse(this.containerStatusService.update(containerStatus));
    } else {
      this.subscribeToSaveResponse(this.containerStatusService.create(containerStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainerStatus>>): void {
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

  protected updateForm(containerStatus: IContainerStatus): void {
    this.containerStatus = containerStatus;
    this.containerStatusFormService.resetForm(this.editForm, containerStatus);

    this.containerStatusGroupsSharedCollection =
      this.containerStatusGroupService.addContainerStatusGroupToCollectionIfMissing<IContainerStatusGroup>(
        this.containerStatusGroupsSharedCollection,
        containerStatus.group,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.containerStatusGroupService
      .query()
      .pipe(map((res: HttpResponse<IContainerStatusGroup[]>) => res.body ?? []))
      .pipe(
        map((containerStatusGroups: IContainerStatusGroup[]) =>
          this.containerStatusGroupService.addContainerStatusGroupToCollectionIfMissing<IContainerStatusGroup>(
            containerStatusGroups,
            this.containerStatus?.group,
          ),
        ),
      )
      .subscribe((containerStatusGroups: IContainerStatusGroup[]) => (this.containerStatusGroupsSharedCollection = containerStatusGroups));
  }
}
