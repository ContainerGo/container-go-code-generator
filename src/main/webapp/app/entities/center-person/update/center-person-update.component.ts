import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICenterPersonGroup } from 'app/entities/center-person-group/center-person-group.model';
import { CenterPersonGroupService } from 'app/entities/center-person-group/service/center-person-group.service';
import { ICenterPerson } from '../center-person.model';
import { CenterPersonService } from '../service/center-person.service';
import { CenterPersonFormService, CenterPersonFormGroup } from './center-person-form.service';

@Component({
  standalone: true,
  selector: 'jhi-center-person-update',
  templateUrl: './center-person-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CenterPersonUpdateComponent implements OnInit {
  isSaving = false;
  centerPerson: ICenterPerson | null = null;

  centerPersonGroupsSharedCollection: ICenterPersonGroup[] = [];

  protected centerPersonService = inject(CenterPersonService);
  protected centerPersonFormService = inject(CenterPersonFormService);
  protected centerPersonGroupService = inject(CenterPersonGroupService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CenterPersonFormGroup = this.centerPersonFormService.createCenterPersonFormGroup();

  compareCenterPersonGroup = (o1: ICenterPersonGroup | null, o2: ICenterPersonGroup | null): boolean =>
    this.centerPersonGroupService.compareCenterPersonGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centerPerson }) => {
      this.centerPerson = centerPerson;
      if (centerPerson) {
        this.updateForm(centerPerson);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centerPerson = this.centerPersonFormService.getCenterPerson(this.editForm);
    if (centerPerson.id !== null) {
      this.subscribeToSaveResponse(this.centerPersonService.update(centerPerson));
    } else {
      this.subscribeToSaveResponse(this.centerPersonService.create(centerPerson));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICenterPerson>>): void {
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

  protected updateForm(centerPerson: ICenterPerson): void {
    this.centerPerson = centerPerson;
    this.centerPersonFormService.resetForm(this.editForm, centerPerson);

    this.centerPersonGroupsSharedCollection = this.centerPersonGroupService.addCenterPersonGroupToCollectionIfMissing<ICenterPersonGroup>(
      this.centerPersonGroupsSharedCollection,
      ...(centerPerson.groups ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.centerPersonGroupService
      .query()
      .pipe(map((res: HttpResponse<ICenterPersonGroup[]>) => res.body ?? []))
      .pipe(
        map((centerPersonGroups: ICenterPersonGroup[]) =>
          this.centerPersonGroupService.addCenterPersonGroupToCollectionIfMissing<ICenterPersonGroup>(
            centerPersonGroups,
            ...(this.centerPerson?.groups ?? []),
          ),
        ),
      )
      .subscribe((centerPersonGroups: ICenterPersonGroup[]) => (this.centerPersonGroupsSharedCollection = centerPersonGroups));
  }
}
