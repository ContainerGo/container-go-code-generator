import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IShipmentHistory, NewShipmentHistory } from '../shipment-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipmentHistory for edit and NewShipmentHistoryFormGroupInput for create.
 */
type ShipmentHistoryFormGroupInput = IShipmentHistory | PartialWithRequiredKeyOf<NewShipmentHistory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IShipmentHistory | NewShipmentHistory> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ShipmentHistoryFormRawValue = FormValueOf<IShipmentHistory>;

type NewShipmentHistoryFormRawValue = FormValueOf<NewShipmentHistory>;

type ShipmentHistoryFormDefaults = Pick<NewShipmentHistory, 'id' | 'timestamp'>;

type ShipmentHistoryFormGroupContent = {
  id: FormControl<ShipmentHistoryFormRawValue['id'] | NewShipmentHistory['id']>;
  event: FormControl<ShipmentHistoryFormRawValue['event']>;
  timestamp: FormControl<ShipmentHistoryFormRawValue['timestamp']>;
  executedBy: FormControl<ShipmentHistoryFormRawValue['executedBy']>;
  location: FormControl<ShipmentHistoryFormRawValue['location']>;
  lat: FormControl<ShipmentHistoryFormRawValue['lat']>;
  lng: FormControl<ShipmentHistoryFormRawValue['lng']>;
  container: FormControl<ShipmentHistoryFormRawValue['container']>;
};

export type ShipmentHistoryFormGroup = FormGroup<ShipmentHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipmentHistoryFormService {
  createShipmentHistoryFormGroup(shipmentHistory: ShipmentHistoryFormGroupInput = { id: null }): ShipmentHistoryFormGroup {
    const shipmentHistoryRawValue = this.convertShipmentHistoryToShipmentHistoryRawValue({
      ...this.getFormDefaults(),
      ...shipmentHistory,
    });
    return new FormGroup<ShipmentHistoryFormGroupContent>({
      id: new FormControl(
        { value: shipmentHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      event: new FormControl(shipmentHistoryRawValue.event, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(shipmentHistoryRawValue.timestamp, {
        validators: [Validators.required],
      }),
      executedBy: new FormControl(shipmentHistoryRawValue.executedBy, {
        validators: [Validators.required],
      }),
      location: new FormControl(shipmentHistoryRawValue.location),
      lat: new FormControl(shipmentHistoryRawValue.lat),
      lng: new FormControl(shipmentHistoryRawValue.lng),
      container: new FormControl(shipmentHistoryRawValue.container, {
        validators: [Validators.required],
      }),
    });
  }

  getShipmentHistory(form: ShipmentHistoryFormGroup): IShipmentHistory | NewShipmentHistory {
    return this.convertShipmentHistoryRawValueToShipmentHistory(
      form.getRawValue() as ShipmentHistoryFormRawValue | NewShipmentHistoryFormRawValue,
    );
  }

  resetForm(form: ShipmentHistoryFormGroup, shipmentHistory: ShipmentHistoryFormGroupInput): void {
    const shipmentHistoryRawValue = this.convertShipmentHistoryToShipmentHistoryRawValue({ ...this.getFormDefaults(), ...shipmentHistory });
    form.reset(
      {
        ...shipmentHistoryRawValue,
        id: { value: shipmentHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipmentHistoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertShipmentHistoryRawValueToShipmentHistory(
    rawShipmentHistory: ShipmentHistoryFormRawValue | NewShipmentHistoryFormRawValue,
  ): IShipmentHistory | NewShipmentHistory {
    return {
      ...rawShipmentHistory,
      timestamp: dayjs(rawShipmentHistory.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertShipmentHistoryToShipmentHistoryRawValue(
    shipmentHistory: IShipmentHistory | (Partial<NewShipmentHistory> & ShipmentHistoryFormDefaults),
  ): ShipmentHistoryFormRawValue | PartialWithRequiredKeyOf<NewShipmentHistoryFormRawValue> {
    return {
      ...shipmentHistory,
      timestamp: shipmentHistory.timestamp ? shipmentHistory.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
