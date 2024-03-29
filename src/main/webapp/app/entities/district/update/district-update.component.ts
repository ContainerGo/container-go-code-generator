import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDistrict } from '../district.model';
import { DistrictService } from '../service/district.service';
import { DistrictFormService, DistrictFormGroup } from './district-form.service';

@Component({
  standalone: true,
  selector: 'jhi-district-update',
  templateUrl: './district-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DistrictUpdateComponent implements OnInit {
  isSaving = false;
  district: IDistrict | null = null;

  protected districtService = inject(DistrictService);
  protected districtFormService = inject(DistrictFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DistrictFormGroup = this.districtFormService.createDistrictFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ district }) => {
      this.district = district;
      if (district) {
        this.updateForm(district);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const district = this.districtFormService.getDistrict(this.editForm);
    if (district.id !== null) {
      this.subscribeToSaveResponse(this.districtService.update(district));
    } else {
      this.subscribeToSaveResponse(this.districtService.create(district));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrict>>): void {
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

  protected updateForm(district: IDistrict): void {
    this.district = district;
    this.districtFormService.resetForm(this.editForm, district);
  }
}
