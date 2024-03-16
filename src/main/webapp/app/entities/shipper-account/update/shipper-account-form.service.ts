import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShipperAccount, NewShipperAccount } from '../shipper-account.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipperAccount for edit and NewShipperAccountFormGroupInput for create.
 */
type ShipperAccountFormGroupInput = IShipperAccount | PartialWithRequiredKeyOf<NewShipperAccount>;

type ShipperAccountFormDefaults = Pick<NewShipperAccount, 'id'>;

type ShipperAccountFormGroupContent = {
  id: FormControl<IShipperAccount['id'] | NewShipperAccount['id']>;
  name: FormControl<IShipperAccount['name']>;
  phone: FormControl<IShipperAccount['phone']>;
  email: FormControl<IShipperAccount['email']>;
  address: FormControl<IShipperAccount['address']>;
  shipper: FormControl<IShipperAccount['shipper']>;
};

export type ShipperAccountFormGroup = FormGroup<ShipperAccountFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipperAccountFormService {
  createShipperAccountFormGroup(shipperAccount: ShipperAccountFormGroupInput = { id: null }): ShipperAccountFormGroup {
    const shipperAccountRawValue = {
      ...this.getFormDefaults(),
      ...shipperAccount,
    };
    return new FormGroup<ShipperAccountFormGroupContent>({
      id: new FormControl(
        { value: shipperAccountRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(shipperAccountRawValue.name, {
        validators: [Validators.required],
      }),
      phone: new FormControl(shipperAccountRawValue.phone, {
        validators: [Validators.required],
      }),
      email: new FormControl(shipperAccountRawValue.email),
      address: new FormControl(shipperAccountRawValue.address),
      shipper: new FormControl(shipperAccountRawValue.shipper, {
        validators: [Validators.required],
      }),
    });
  }

  getShipperAccount(form: ShipperAccountFormGroup): IShipperAccount | NewShipperAccount {
    return form.getRawValue() as IShipperAccount | NewShipperAccount;
  }

  resetForm(form: ShipperAccountFormGroup, shipperAccount: ShipperAccountFormGroupInput): void {
    const shipperAccountRawValue = { ...this.getFormDefaults(), ...shipperAccount };
    form.reset(
      {
        ...shipperAccountRawValue,
        id: { value: shipperAccountRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipperAccountFormDefaults {
    return {
      id: null,
    };
  }
}
