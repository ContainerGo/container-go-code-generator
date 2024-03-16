import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICenterPersonGroup } from '../center-person-group.model';
import { CenterPersonGroupService } from '../service/center-person-group.service';
import { CenterPersonGroupFormService, CenterPersonGroupFormGroup } from './center-person-group-form.service';

@Component({
  standalone: true,
  selector: 'jhi-center-person-group-update',
  templateUrl: './center-person-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CenterPersonGroupUpdateComponent implements OnInit {
  isSaving = false;
  centerPersonGroup: ICenterPersonGroup | null = null;

  editForm: CenterPersonGroupFormGroup = this.centerPersonGroupFormService.createCenterPersonGroupFormGroup();

  constructor(
    protected centerPersonGroupService: CenterPersonGroupService,
    protected centerPersonGroupFormService: CenterPersonGroupFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centerPersonGroup }) => {
      this.centerPersonGroup = centerPersonGroup;
      if (centerPersonGroup) {
        this.updateForm(centerPersonGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centerPersonGroup = this.centerPersonGroupFormService.getCenterPersonGroup(this.editForm);
    if (centerPersonGroup.id !== null) {
      this.subscribeToSaveResponse(this.centerPersonGroupService.update(centerPersonGroup));
    } else {
      this.subscribeToSaveResponse(this.centerPersonGroupService.create(centerPersonGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICenterPersonGroup>>): void {
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

  protected updateForm(centerPersonGroup: ICenterPersonGroup): void {
    this.centerPersonGroup = centerPersonGroup;
    this.centerPersonGroupFormService.resetForm(this.editForm, centerPersonGroup);
  }
}
