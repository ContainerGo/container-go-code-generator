import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICarrierPersonGroup, NewCarrierPersonGroup } from '../carrier-person-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICarrierPersonGroup for edit and NewCarrierPersonGroupFormGroupInput for create.
 */
type CarrierPersonGroupFormGroupInput = ICarrierPersonGroup | PartialWithRequiredKeyOf<NewCarrierPersonGroup>;

type CarrierPersonGroupFormDefaults = Pick<NewCarrierPersonGroup, 'id'>;

type CarrierPersonGroupFormGroupContent = {
  id: FormControl<ICarrierPersonGroup['id'] | NewCarrierPersonGroup['id']>;
  name: FormControl<ICarrierPersonGroup['name']>;
};

export type CarrierPersonGroupFormGroup = FormGroup<CarrierPersonGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarrierPersonGroupFormService {
  createCarrierPersonGroupFormGroup(carrierPersonGroup: CarrierPersonGroupFormGroupInput = { id: null }): CarrierPersonGroupFormGroup {
    const carrierPersonGroupRawValue = {
      ...this.getFormDefaults(),
      ...carrierPersonGroup,
    };
    return new FormGroup<CarrierPersonGroupFormGroupContent>({
      id: new FormControl(
        { value: carrierPersonGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(carrierPersonGroupRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getCarrierPersonGroup(form: CarrierPersonGroupFormGroup): ICarrierPersonGroup | NewCarrierPersonGroup {
    return form.getRawValue() as ICarrierPersonGroup | NewCarrierPersonGroup;
  }

  resetForm(form: CarrierPersonGroupFormGroup, carrierPersonGroup: CarrierPersonGroupFormGroupInput): void {
    const carrierPersonGroupRawValue = { ...this.getFormDefaults(), ...carrierPersonGroup };
    form.reset(
      {
        ...carrierPersonGroupRawValue,
        id: { value: carrierPersonGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CarrierPersonGroupFormDefaults {
    return {
      id: null,
    };
  }
}
