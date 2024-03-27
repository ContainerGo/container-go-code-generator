import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContainer } from 'app/entities/container/container.model';
import { ContainerService } from 'app/entities/container/service/container.service';
import { OfferState } from 'app/entities/enumerations/offer-state.model';
import { OfferService } from '../service/offer.service';
import { IOffer } from '../offer.model';
import { OfferFormService, OfferFormGroup } from './offer-form.service';

@Component({
  standalone: true,
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OfferUpdateComponent implements OnInit {
  isSaving = false;
  offer: IOffer | null = null;
  offerStateValues = Object.keys(OfferState);

  containersSharedCollection: IContainer[] = [];

  protected offerService = inject(OfferService);
  protected offerFormService = inject(OfferFormService);
  protected containerService = inject(ContainerService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OfferFormGroup = this.offerFormService.createOfferFormGroup();

  compareContainer = (o1: IContainer | null, o2: IContainer | null): boolean => this.containerService.compareContainer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.offer = offer;
      if (offer) {
        this.updateForm(offer);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offer = this.offerFormService.getOffer(this.editForm);
    if (offer.id !== null) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>): void {
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

  protected updateForm(offer: IOffer): void {
    this.offer = offer;
    this.offerFormService.resetForm(this.editForm, offer);

    this.containersSharedCollection = this.containerService.addContainerToCollectionIfMissing<IContainer>(
      this.containersSharedCollection,
      offer.container,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.containerService
      .query()
      .pipe(map((res: HttpResponse<IContainer[]>) => res.body ?? []))
      .pipe(
        map((containers: IContainer[]) =>
          this.containerService.addContainerToCollectionIfMissing<IContainer>(containers, this.offer?.container),
        ),
      )
      .subscribe((containers: IContainer[]) => (this.containersSharedCollection = containers));
  }
}
