import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICarrierAccount, NewCarrierAccount } from '../carrier-account.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICarrierAccount for edit and NewCarrierAccountFormGroupInput for create.
 */
type CarrierAccountFormGroupInput = ICarrierAccount | PartialWithRequiredKeyOf<NewCarrierAccount>;

type CarrierAccountFormDefaults = Pick<NewCarrierAccount, 'id'>;

type CarrierAccountFormGroupContent = {
  id: FormControl<ICarrierAccount['id'] | NewCarrierAccount['id']>;
  name: FormControl<ICarrierAccount['name']>;
  phone: FormControl<ICarrierAccount['phone']>;
  email: FormControl<ICarrierAccount['email']>;
  address: FormControl<ICarrierAccount['address']>;
  carrier: FormControl<ICarrierAccount['carrier']>;
};

export type CarrierAccountFormGroup = FormGroup<CarrierAccountFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarrierAccountFormService {
  createCarrierAccountFormGroup(carrierAccount: CarrierAccountFormGroupInput = { id: null }): CarrierAccountFormGroup {
    const carrierAccountRawValue = {
      ...this.getFormDefaults(),
      ...carrierAccount,
    };
    return new FormGroup<CarrierAccountFormGroupContent>({
      id: new FormControl(
        { value: carrierAccountRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(carrierAccountRawValue.name, {
        validators: [Validators.required],
      }),
      phone: new FormControl(carrierAccountRawValue.phone, {
        validators: [Validators.required],
      }),
      email: new FormControl(carrierAccountRawValue.email),
      address: new FormControl(carrierAccountRawValue.address),
      carrier: new FormControl(carrierAccountRawValue.carrier, {
        validators: [Validators.required],
      }),
    });
  }

  getCarrierAccount(form: CarrierAccountFormGroup): ICarrierAccount | NewCarrierAccount {
    return form.getRawValue() as ICarrierAccount | NewCarrierAccount;
  }

  resetForm(form: CarrierAccountFormGroup, carrierAccount: CarrierAccountFormGroupInput): void {
    const carrierAccountRawValue = { ...this.getFormDefaults(), ...carrierAccount };
    form.reset(
      {
        ...carrierAccountRawValue,
        id: { value: carrierAccountRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CarrierAccountFormDefaults {
    return {
      id: null,
    };
  }
}
