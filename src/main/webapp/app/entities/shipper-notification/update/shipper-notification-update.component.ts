import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IShipperPerson } from 'app/entities/shipper-person/shipper-person.model';
import { ShipperPersonService } from 'app/entities/shipper-person/service/shipper-person.service';
import { IShipperNotification } from '../shipper-notification.model';
import { ShipperNotificationService } from '../service/shipper-notification.service';
import { ShipperNotificationFormService, ShipperNotificationFormGroup } from './shipper-notification-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipper-notification-update',
  templateUrl: './shipper-notification-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipperNotificationUpdateComponent implements OnInit {
  isSaving = false;
  shipperNotification: IShipperNotification | null = null;

  shipperPeopleSharedCollection: IShipperPerson[] = [];

  protected shipperNotificationService = inject(ShipperNotificationService);
  protected shipperNotificationFormService = inject(ShipperNotificationFormService);
  protected shipperPersonService = inject(ShipperPersonService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ShipperNotificationFormGroup = this.shipperNotificationFormService.createShipperNotificationFormGroup();

  compareShipperPerson = (o1: IShipperPerson | null, o2: IShipperPerson | null): boolean =>
    this.shipperPersonService.compareShipperPerson(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipperNotification }) => {
      this.shipperNotification = shipperNotification;
      if (shipperNotification) {
        this.updateForm(shipperNotification);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipperNotification = this.shipperNotificationFormService.getShipperNotification(this.editForm);
    if (shipperNotification.id !== null) {
      this.subscribeToSaveResponse(this.shipperNotificationService.update(shipperNotification));
    } else {
      this.subscribeToSaveResponse(this.shipperNotificationService.create(shipperNotification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipperNotification>>): void {
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

  protected updateForm(shipperNotification: IShipperNotification): void {
    this.shipperNotification = shipperNotification;
    this.shipperNotificationFormService.resetForm(this.editForm, shipperNotification);

    this.shipperPeopleSharedCollection = this.shipperPersonService.addShipperPersonToCollectionIfMissing<IShipperPerson>(
      this.shipperPeopleSharedCollection,
      shipperNotification.person,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.shipperPersonService
      .query()
      .pipe(map((res: HttpResponse<IShipperPerson[]>) => res.body ?? []))
      .pipe(
        map((shipperPeople: IShipperPerson[]) =>
          this.shipperPersonService.addShipperPersonToCollectionIfMissing<IShipperPerson>(shipperPeople, this.shipperNotification?.person),
        ),
      )
      .subscribe((shipperPeople: IShipperPerson[]) => (this.shipperPeopleSharedCollection = shipperPeople));
  }
}
