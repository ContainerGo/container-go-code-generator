import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
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

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICarrier | NewCarrier> = Omit<T, 'verifiedSince'> & {
  verifiedSince?: string | null;
};

type CarrierFormRawValue = FormValueOf<ICarrier>;

type NewCarrierFormRawValue = FormValueOf<NewCarrier>;

type CarrierFormDefaults = Pick<NewCarrier, 'id' | 'isApproved' | 'verifiedSince'>;

type CarrierFormGroupContent = {
  id: FormControl<CarrierFormRawValue['id'] | NewCarrier['id']>;
  code: FormControl<CarrierFormRawValue['code']>;
  name: FormControl<CarrierFormRawValue['name']>;
  address: FormControl<CarrierFormRawValue['address']>;
  taxCode: FormControl<CarrierFormRawValue['taxCode']>;
  bankAccount: FormControl<CarrierFormRawValue['bankAccount']>;
  bankName: FormControl<CarrierFormRawValue['bankName']>;
  accountName: FormControl<CarrierFormRawValue['accountName']>;
  branchName: FormControl<CarrierFormRawValue['branchName']>;
  companySize: FormControl<CarrierFormRawValue['companySize']>;
  isApproved: FormControl<CarrierFormRawValue['isApproved']>;
  vehicles: FormControl<CarrierFormRawValue['vehicles']>;
  shipmentsLeftForDay: FormControl<CarrierFormRawValue['shipmentsLeftForDay']>;
  verifiedSince: FormControl<CarrierFormRawValue['verifiedSince']>;
};

export type CarrierFormGroup = FormGroup<CarrierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarrierFormService {
  createCarrierFormGroup(carrier: CarrierFormGroupInput = { id: null }): CarrierFormGroup {
    const carrierRawValue = this.convertCarrierToCarrierRawValue({
      ...this.getFormDefaults(),
      ...carrier,
    });
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
      vehicles: new FormControl(carrierRawValue.vehicles),
      shipmentsLeftForDay: new FormControl(carrierRawValue.shipmentsLeftForDay),
      verifiedSince: new FormControl(carrierRawValue.verifiedSince),
    });
  }

  getCarrier(form: CarrierFormGroup): ICarrier | NewCarrier {
    return this.convertCarrierRawValueToCarrier(form.getRawValue() as CarrierFormRawValue | NewCarrierFormRawValue);
  }

  resetForm(form: CarrierFormGroup, carrier: CarrierFormGroupInput): void {
    const carrierRawValue = this.convertCarrierToCarrierRawValue({ ...this.getFormDefaults(), ...carrier });
    form.reset(
      {
        ...carrierRawValue,
        id: { value: carrierRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CarrierFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isApproved: false,
      verifiedSince: currentTime,
    };
  }

  private convertCarrierRawValueToCarrier(rawCarrier: CarrierFormRawValue | NewCarrierFormRawValue): ICarrier | NewCarrier {
    return {
      ...rawCarrier,
      verifiedSince: dayjs(rawCarrier.verifiedSince, DATE_TIME_FORMAT),
    };
  }

  private convertCarrierToCarrierRawValue(
    carrier: ICarrier | (Partial<NewCarrier> & CarrierFormDefaults),
  ): CarrierFormRawValue | PartialWithRequiredKeyOf<NewCarrierFormRawValue> {
    return {
      ...carrier,
      verifiedSince: carrier.verifiedSince ? carrier.verifiedSince.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
