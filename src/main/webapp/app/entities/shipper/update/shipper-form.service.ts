import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShipper, NewShipper } from '../shipper.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipper for edit and NewShipperFormGroupInput for create.
 */
type ShipperFormGroupInput = IShipper | PartialWithRequiredKeyOf<NewShipper>;

type ShipperFormDefaults = Pick<NewShipper, 'id' | 'isApproved' | 'isBillingInformationComplete' | 'isProfileComplete'>;

type ShipperFormGroupContent = {
  id: FormControl<IShipper['id'] | NewShipper['id']>;
  code: FormControl<IShipper['code']>;
  name: FormControl<IShipper['name']>;
  address: FormControl<IShipper['address']>;
  taxCode: FormControl<IShipper['taxCode']>;
  companySize: FormControl<IShipper['companySize']>;
  paymentType: FormControl<IShipper['paymentType']>;
  isApproved: FormControl<IShipper['isApproved']>;
  isBillingInformationComplete: FormControl<IShipper['isBillingInformationComplete']>;
  isProfileComplete: FormControl<IShipper['isProfileComplete']>;
};

export type ShipperFormGroup = FormGroup<ShipperFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipperFormService {
  createShipperFormGroup(shipper: ShipperFormGroupInput = { id: null }): ShipperFormGroup {
    const shipperRawValue = {
      ...this.getFormDefaults(),
      ...shipper,
    };
    return new FormGroup<ShipperFormGroupContent>({
      id: new FormControl(
        { value: shipperRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(shipperRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(shipperRawValue.name, {
        validators: [Validators.required],
      }),
      address: new FormControl(shipperRawValue.address, {
        validators: [Validators.required],
      }),
      taxCode: new FormControl(shipperRawValue.taxCode),
      companySize: new FormControl(shipperRawValue.companySize),
      paymentType: new FormControl(shipperRawValue.paymentType, {
        validators: [Validators.required],
      }),
      isApproved: new FormControl(shipperRawValue.isApproved),
      isBillingInformationComplete: new FormControl(shipperRawValue.isBillingInformationComplete),
      isProfileComplete: new FormControl(shipperRawValue.isProfileComplete),
    });
  }

  getShipper(form: ShipperFormGroup): IShipper | NewShipper {
    return form.getRawValue() as IShipper | NewShipper;
  }

  resetForm(form: ShipperFormGroup, shipper: ShipperFormGroupInput): void {
    const shipperRawValue = { ...this.getFormDefaults(), ...shipper };
    form.reset(
      {
        ...shipperRawValue,
        id: { value: shipperRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipperFormDefaults {
    return {
      id: null,
      isApproved: false,
      isBillingInformationComplete: false,
      isProfileComplete: false,
    };
  }
}
