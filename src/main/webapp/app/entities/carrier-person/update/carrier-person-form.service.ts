import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICarrierPerson, NewCarrierPerson } from '../carrier-person.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICarrierPerson for edit and NewCarrierPersonFormGroupInput for create.
 */
type CarrierPersonFormGroupInput = ICarrierPerson | PartialWithRequiredKeyOf<NewCarrierPerson>;

type CarrierPersonFormDefaults = Pick<NewCarrierPerson, 'id'>;

type CarrierPersonFormGroupContent = {
  id: FormControl<ICarrierPerson['id'] | NewCarrierPerson['id']>;
  name: FormControl<ICarrierPerson['name']>;
  phone: FormControl<ICarrierPerson['phone']>;
  email: FormControl<ICarrierPerson['email']>;
  address: FormControl<ICarrierPerson['address']>;
  carrier: FormControl<ICarrierPerson['carrier']>;
};

export type CarrierPersonFormGroup = FormGroup<CarrierPersonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarrierPersonFormService {
  createCarrierPersonFormGroup(carrierPerson: CarrierPersonFormGroupInput = { id: null }): CarrierPersonFormGroup {
    const carrierPersonRawValue = {
      ...this.getFormDefaults(),
      ...carrierPerson,
    };
    return new FormGroup<CarrierPersonFormGroupContent>({
      id: new FormControl(
        { value: carrierPersonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(carrierPersonRawValue.name, {
        validators: [Validators.required],
      }),
      phone: new FormControl(carrierPersonRawValue.phone, {
        validators: [Validators.required],
      }),
      email: new FormControl(carrierPersonRawValue.email),
      address: new FormControl(carrierPersonRawValue.address),
      carrier: new FormControl(carrierPersonRawValue.carrier),
    });
  }

  getCarrierPerson(form: CarrierPersonFormGroup): ICarrierPerson | NewCarrierPerson {
    return form.getRawValue() as ICarrierPerson | NewCarrierPerson;
  }

  resetForm(form: CarrierPersonFormGroup, carrierPerson: CarrierPersonFormGroupInput): void {
    const carrierPersonRawValue = { ...this.getFormDefaults(), ...carrierPerson };
    form.reset(
      {
        ...carrierPersonRawValue,
        id: { value: carrierPersonRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CarrierPersonFormDefaults {
    return {
      id: null,
    };
  }
}
