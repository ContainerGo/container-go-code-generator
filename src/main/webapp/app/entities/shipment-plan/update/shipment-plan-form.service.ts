import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IShipmentPlan, NewShipmentPlan } from '../shipment-plan.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipmentPlan for edit and NewShipmentPlanFormGroupInput for create.
 */
type ShipmentPlanFormGroupInput = IShipmentPlan | PartialWithRequiredKeyOf<NewShipmentPlan>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IShipmentPlan | NewShipmentPlan> = Omit<
  T,
  'estimatedPickupFromDate' | 'estimatedPickupUntilDate' | 'estimatedDropoffFromDate' | 'estimatedDropoffUntilDate'
> & {
  estimatedPickupFromDate?: string | null;
  estimatedPickupUntilDate?: string | null;
  estimatedDropoffFromDate?: string | null;
  estimatedDropoffUntilDate?: string | null;
};

type ShipmentPlanFormRawValue = FormValueOf<IShipmentPlan>;

type NewShipmentPlanFormRawValue = FormValueOf<NewShipmentPlan>;

type ShipmentPlanFormDefaults = Pick<
  NewShipmentPlan,
  'id' | 'estimatedPickupFromDate' | 'estimatedPickupUntilDate' | 'estimatedDropoffFromDate' | 'estimatedDropoffUntilDate'
>;

type ShipmentPlanFormGroupContent = {
  id: FormControl<ShipmentPlanFormRawValue['id'] | NewShipmentPlan['id']>;
  estimatedPickupFromDate: FormControl<ShipmentPlanFormRawValue['estimatedPickupFromDate']>;
  estimatedPickupUntilDate: FormControl<ShipmentPlanFormRawValue['estimatedPickupUntilDate']>;
  estimatedDropoffFromDate: FormControl<ShipmentPlanFormRawValue['estimatedDropoffFromDate']>;
  estimatedDropoffUntilDate: FormControl<ShipmentPlanFormRawValue['estimatedDropoffUntilDate']>;
  driverId: FormControl<ShipmentPlanFormRawValue['driverId']>;
  truckId: FormControl<ShipmentPlanFormRawValue['truckId']>;
  container: FormControl<ShipmentPlanFormRawValue['container']>;
};

export type ShipmentPlanFormGroup = FormGroup<ShipmentPlanFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipmentPlanFormService {
  createShipmentPlanFormGroup(shipmentPlan: ShipmentPlanFormGroupInput = { id: null }): ShipmentPlanFormGroup {
    const shipmentPlanRawValue = this.convertShipmentPlanToShipmentPlanRawValue({
      ...this.getFormDefaults(),
      ...shipmentPlan,
    });
    return new FormGroup<ShipmentPlanFormGroupContent>({
      id: new FormControl(
        { value: shipmentPlanRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      estimatedPickupFromDate: new FormControl(shipmentPlanRawValue.estimatedPickupFromDate, {
        validators: [Validators.required],
      }),
      estimatedPickupUntilDate: new FormControl(shipmentPlanRawValue.estimatedPickupUntilDate, {
        validators: [Validators.required],
      }),
      estimatedDropoffFromDate: new FormControl(shipmentPlanRawValue.estimatedDropoffFromDate, {
        validators: [Validators.required],
      }),
      estimatedDropoffUntilDate: new FormControl(shipmentPlanRawValue.estimatedDropoffUntilDate, {
        validators: [Validators.required],
      }),
      driverId: new FormControl(shipmentPlanRawValue.driverId, {
        validators: [Validators.required],
      }),
      truckId: new FormControl(shipmentPlanRawValue.truckId, {
        validators: [Validators.required],
      }),
      container: new FormControl(shipmentPlanRawValue.container),
    });
  }

  getShipmentPlan(form: ShipmentPlanFormGroup): IShipmentPlan | NewShipmentPlan {
    return this.convertShipmentPlanRawValueToShipmentPlan(form.getRawValue() as ShipmentPlanFormRawValue | NewShipmentPlanFormRawValue);
  }

  resetForm(form: ShipmentPlanFormGroup, shipmentPlan: ShipmentPlanFormGroupInput): void {
    const shipmentPlanRawValue = this.convertShipmentPlanToShipmentPlanRawValue({ ...this.getFormDefaults(), ...shipmentPlan });
    form.reset(
      {
        ...shipmentPlanRawValue,
        id: { value: shipmentPlanRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipmentPlanFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      estimatedPickupFromDate: currentTime,
      estimatedPickupUntilDate: currentTime,
      estimatedDropoffFromDate: currentTime,
      estimatedDropoffUntilDate: currentTime,
    };
  }

  private convertShipmentPlanRawValueToShipmentPlan(
    rawShipmentPlan: ShipmentPlanFormRawValue | NewShipmentPlanFormRawValue,
  ): IShipmentPlan | NewShipmentPlan {
    return {
      ...rawShipmentPlan,
      estimatedPickupFromDate: dayjs(rawShipmentPlan.estimatedPickupFromDate, DATE_TIME_FORMAT),
      estimatedPickupUntilDate: dayjs(rawShipmentPlan.estimatedPickupUntilDate, DATE_TIME_FORMAT),
      estimatedDropoffFromDate: dayjs(rawShipmentPlan.estimatedDropoffFromDate, DATE_TIME_FORMAT),
      estimatedDropoffUntilDate: dayjs(rawShipmentPlan.estimatedDropoffUntilDate, DATE_TIME_FORMAT),
    };
  }

  private convertShipmentPlanToShipmentPlanRawValue(
    shipmentPlan: IShipmentPlan | (Partial<NewShipmentPlan> & ShipmentPlanFormDefaults),
  ): ShipmentPlanFormRawValue | PartialWithRequiredKeyOf<NewShipmentPlanFormRawValue> {
    return {
      ...shipmentPlan,
      estimatedPickupFromDate: shipmentPlan.estimatedPickupFromDate
        ? shipmentPlan.estimatedPickupFromDate.format(DATE_TIME_FORMAT)
        : undefined,
      estimatedPickupUntilDate: shipmentPlan.estimatedPickupUntilDate
        ? shipmentPlan.estimatedPickupUntilDate.format(DATE_TIME_FORMAT)
        : undefined,
      estimatedDropoffFromDate: shipmentPlan.estimatedDropoffFromDate
        ? shipmentPlan.estimatedDropoffFromDate.format(DATE_TIME_FORMAT)
        : undefined,
      estimatedDropoffUntilDate: shipmentPlan.estimatedDropoffUntilDate
        ? shipmentPlan.estimatedDropoffUntilDate.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
