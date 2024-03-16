import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICarrier, NewCarrier } from '../carrier.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICarrier for edit and NewCarrierFormGroupInput for create.
 */
type CarrierFormGroupInput = ICarrier | PartialWithRequiredKeyOf<NewCarrier>;

type CarrierFormDefaults = Pick<NewCarrier, 'id' | 'isApproved'>;

type CarrierFormGroupContent = {
  id: FormControl<ICarrier['id'] | NewCarrier['id']>;
  code: FormControl<ICarrier['code']>;
  name: FormControl<ICarrier['name']>;
  address: FormControl<ICarrier['address']>;
  taxCode: FormControl<ICarrier['taxCode']>;
  bankAccount: FormControl<ICarrier['bankAccount']>;
  bankName: FormControl<ICarrier['bankName']>;
  accountName: FormControl<ICarrier['accountName']>;
  branchName: FormControl<ICarrier['branchName']>;
  companySize: FormControl<ICarrier['companySize']>;
  isApproved: FormControl<ICarrier['isApproved']>;
};

export type CarrierFormGroup = FormGroup<CarrierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarrierFormService {
  createCarrierFormGroup(carrier: CarrierFormGroupInput = { id: null }): CarrierFormGroup {
    const carrierRawValue = {
      ...this.getFormDefaults(),
      ...carrier,
    };
    return new FormGroup<CarrierFormGroupContent>({
      id: new FormControl(
        { value: carrierRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(carrierRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(carrierRawValue.name, {
        validators: [Validators.required],
      }),
      address: new FormControl(carrierRawValue.address, {
        validators: [Validators.required],
      }),
      taxCode: new FormControl(carrierRawValue.taxCode),
      bankAccount: new FormControl(carrierRawValue.bankAccount),
      bankName: new FormControl(carrierRawValue.bankName),
      accountName: new FormControl(carrierRawValue.accountName),
      branchName: new FormControl(carrierRawValue.branchName),
      companySize: new FormControl(carrierRawValue.companySize),
      isApproved: new FormControl(carrierRawValue.isApproved),
    });
  }

  getCarrier(form: CarrierFormGroup): ICarrier | NewCarrier {
    return form.getRawValue() as ICarrier | NewCarrier;
  }

  resetForm(form: CarrierFormGroup, carrier: CarrierFormGroupInput): void {
    const carrierRawValue = { ...this.getFormDefaults(), ...carrier };
    form.reset(
      {
        ...carrierRawValue,
        id: { value: carrierRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CarrierFormDefaults {
    return {
      id: null,
      isApproved: false,
    };
  }
}
