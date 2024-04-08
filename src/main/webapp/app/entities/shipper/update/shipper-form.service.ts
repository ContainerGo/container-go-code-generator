import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
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

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IShipper | NewShipper> = Omit<T, 'contractValidUntil'> & {
  contractValidUntil?: string | null;
};

type ShipperFormRawValue = FormValueOf<IShipper>;

type NewShipperFormRawValue = FormValueOf<NewShipper>;

type ShipperFormDefaults = Pick<
  NewShipper,
  'id' | 'contractValidUntil' | 'isApproved' | 'isBillingInformationComplete' | 'isProfileComplete'
>;

type ShipperFormGroupContent = {
  id: FormControl<ShipperFormRawValue['id'] | NewShipper['id']>;
  code: FormControl<ShipperFormRawValue['code']>;
  name: FormControl<ShipperFormRawValue['name']>;
  address: FormControl<ShipperFormRawValue['address']>;
  taxCode: FormControl<ShipperFormRawValue['taxCode']>;
  companySize: FormControl<ShipperFormRawValue['companySize']>;
  paymentType: FormControl<ShipperFormRawValue['paymentType']>;
  contractType: FormControl<ShipperFormRawValue['contractType']>;
  contractValidUntil: FormControl<ShipperFormRawValue['contractValidUntil']>;
  isApproved: FormControl<ShipperFormRawValue['isApproved']>;
  isBillingInformationComplete: FormControl<ShipperFormRawValue['isBillingInformationComplete']>;
  isProfileComplete: FormControl<ShipperFormRawValue['isProfileComplete']>;
};

export type ShipperFormGroup = FormGroup<ShipperFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipperFormService {
  createShipperFormGroup(shipper: ShipperFormGroupInput = { id: null }): ShipperFormGroup {
    const shipperRawValue = this.convertShipperToShipperRawValue({
      ...this.getFormDefaults(),
      ...shipper,
    });
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
      contractType: new FormControl(shipperRawValue.contractType, {
        validators: [Validators.required],
      }),
      contractValidUntil: new FormControl(shipperRawValue.contractValidUntil),
      isApproved: new FormControl(shipperRawValue.isApproved),
      isBillingInformationComplete: new FormControl(shipperRawValue.isBillingInformationComplete),
      isProfileComplete: new FormControl(shipperRawValue.isProfileComplete),
    });
  }

  getShipper(form: ShipperFormGroup): IShipper | NewShipper {
    return this.convertShipperRawValueToShipper(form.getRawValue() as ShipperFormRawValue | NewShipperFormRawValue);
  }

  resetForm(form: ShipperFormGroup, shipper: ShipperFormGroupInput): void {
    const shipperRawValue = this.convertShipperToShipperRawValue({ ...this.getFormDefaults(), ...shipper });
    form.reset(
      {
        ...shipperRawValue,
        id: { value: shipperRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipperFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      contractValidUntil: currentTime,
      isApproved: false,
      isBillingInformationComplete: false,
      isProfileComplete: false,
    };
  }

  private convertShipperRawValueToShipper(rawShipper: ShipperFormRawValue | NewShipperFormRawValue): IShipper | NewShipper {
    return {
      ...rawShipper,
      contractValidUntil: dayjs(rawShipper.contractValidUntil, DATE_TIME_FORMAT),
    };
  }

  private convertShipperToShipperRawValue(
    shipper: IShipper | (Partial<NewShipper> & ShipperFormDefaults),
  ): ShipperFormRawValue | PartialWithRequiredKeyOf<NewShipperFormRawValue> {
    return {
      ...shipper,
      contractValidUntil: shipper.contractValidUntil ? shipper.contractValidUntil.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
