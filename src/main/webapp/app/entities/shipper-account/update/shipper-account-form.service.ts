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
  balance: FormControl<IShipperAccount['balance']>;
  accountType: FormControl<IShipperAccount['accountType']>;
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
      balance: new FormControl(shipperAccountRawValue.balance, {
        validators: [Validators.required],
      }),
      accountType: new FormControl(shipperAccountRawValue.accountType, {
        validators: [Validators.required],
      }),
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
