import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITruck, NewTruck } from '../truck.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITruck for edit and NewTruckFormGroupInput for create.
 */
type TruckFormGroupInput = ITruck | PartialWithRequiredKeyOf<NewTruck>;

type TruckFormDefaults = Pick<NewTruck, 'id'>;

type TruckFormGroupContent = {
  id: FormControl<ITruck['id'] | NewTruck['id']>;
  code: FormControl<ITruck['code']>;
  name: FormControl<ITruck['name']>;
};

export type TruckFormGroup = FormGroup<TruckFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TruckFormService {
  createTruckFormGroup(truck: TruckFormGroupInput = { id: null }): TruckFormGroup {
    const truckRawValue = {
      ...this.getFormDefaults(),
      ...truck,
    };
    return new FormGroup<TruckFormGroupContent>({
      id: new FormControl(
        { value: truckRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(truckRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(truckRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getTruck(form: TruckFormGroup): ITruck | NewTruck {
    return form.getRawValue() as ITruck | NewTruck;
  }

  resetForm(form: TruckFormGroup, truck: TruckFormGroupInput): void {
    const truckRawValue = { ...this.getFormDefaults(), ...truck };
    form.reset(
      {
        ...truckRawValue,
        id: { value: truckRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TruckFormDefaults {
    return {
      id: null,
    };
  }
}
