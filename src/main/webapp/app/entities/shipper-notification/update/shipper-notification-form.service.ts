import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShipperNotification, NewShipperNotification } from '../shipper-notification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipperNotification for edit and NewShipperNotificationFormGroupInput for create.
 */
type ShipperNotificationFormGroupInput = IShipperNotification | PartialWithRequiredKeyOf<NewShipperNotification>;

type ShipperNotificationFormDefaults = Pick<
  NewShipperNotification,
  'id' | 'isEmailNotificationEnabled' | 'isSmsNotificationEnabled' | 'isAppNotificationEnabled'
>;

type ShipperNotificationFormGroupContent = {
  id: FormControl<IShipperNotification['id'] | NewShipperNotification['id']>;
  code: FormControl<IShipperNotification['code']>;
  name: FormControl<IShipperNotification['name']>;
  isEmailNotificationEnabled: FormControl<IShipperNotification['isEmailNotificationEnabled']>;
  isSmsNotificationEnabled: FormControl<IShipperNotification['isSmsNotificationEnabled']>;
  isAppNotificationEnabled: FormControl<IShipperNotification['isAppNotificationEnabled']>;
  person: FormControl<IShipperNotification['person']>;
};

export type ShipperNotificationFormGroup = FormGroup<ShipperNotificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipperNotificationFormService {
  createShipperNotificationFormGroup(shipperNotification: ShipperNotificationFormGroupInput = { id: null }): ShipperNotificationFormGroup {
    const shipperNotificationRawValue = {
      ...this.getFormDefaults(),
      ...shipperNotification,
    };
    return new FormGroup<ShipperNotificationFormGroupContent>({
      id: new FormControl(
        { value: shipperNotificationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(shipperNotificationRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(shipperNotificationRawValue.name, {
        validators: [Validators.required],
      }),
      isEmailNotificationEnabled: new FormControl(shipperNotificationRawValue.isEmailNotificationEnabled),
      isSmsNotificationEnabled: new FormControl(shipperNotificationRawValue.isSmsNotificationEnabled),
      isAppNotificationEnabled: new FormControl(shipperNotificationRawValue.isAppNotificationEnabled),
      person: new FormControl(shipperNotificationRawValue.person),
    });
  }

  getShipperNotification(form: ShipperNotificationFormGroup): IShipperNotification | NewShipperNotification {
    return form.getRawValue() as IShipperNotification | NewShipperNotification;
  }

  resetForm(form: ShipperNotificationFormGroup, shipperNotification: ShipperNotificationFormGroupInput): void {
    const shipperNotificationRawValue = { ...this.getFormDefaults(), ...shipperNotification };
    form.reset(
      {
        ...shipperNotificationRawValue,
        id: { value: shipperNotificationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipperNotificationFormDefaults {
    return {
      id: null,
      isEmailNotificationEnabled: false,
      isSmsNotificationEnabled: false,
      isAppNotificationEnabled: false,
    };
  }
}
