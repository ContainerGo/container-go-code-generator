import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IShipper } from '../shipper.model';
import { ShipperService } from '../service/shipper.service';
import { ShipperFormService, ShipperFormGroup } from './shipper-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipper-update',
  templateUrl: './shipper-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipperUpdateComponent implements OnInit {
  isSaving = false;
  shipper: IShipper | null = null;

  protected shipperService = inject(ShipperService);
  protected shipperFormService = inject(ShipperFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ShipperFormGroup = this.shipperFormService.createShipperFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipper }) => {
      this.shipper = shipper;
      if (shipper) {
        this.updateForm(shipper);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipper = this.shipperFormService.getShipper(this.editForm);
    if (shipper.id !== null) {
      this.subscribeToSaveResponse(this.shipperService.update(shipper));
    } else {
      this.subscribeToSaveResponse(this.shipperService.create(shipper));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipper>>): void {
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

  protected updateForm(shipper: IShipper): void {
    this.shipper = shipper;
    this.shipperFormService.resetForm(this.editForm, shipper);
  }
}
