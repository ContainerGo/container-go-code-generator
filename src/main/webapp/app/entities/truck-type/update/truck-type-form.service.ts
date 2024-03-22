import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITruckType, NewTruckType } from '../truck-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITruckType for edit and NewTruckTypeFormGroupInput for create.
 */
type TruckTypeFormGroupInput = ITruckType | PartialWithRequiredKeyOf<NewTruckType>;

type TruckTypeFormDefaults = Pick<NewTruckType, 'id'>;

type TruckTypeFormGroupContent = {
  id: FormControl<ITruckType['id'] | NewTruckType['id']>;
  code: FormControl<ITruckType['code']>;
  name: FormControl<ITruckType['name']>;
  category: FormControl<ITruckType['category']>;
  height: FormControl<ITruckType['height']>;
  length: FormControl<ITruckType['length']>;
  maxSpeed: FormControl<ITruckType['maxSpeed']>;
  type: FormControl<ITruckType['type']>;
  weight: FormControl<ITruckType['weight']>;
  width: FormControl<ITruckType['width']>;
};

export type TruckTypeFormGroup = FormGroup<TruckTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TruckTypeFormService {
  createTruckTypeFormGroup(truckType: TruckTypeFormGroupInput = { id: null }): TruckTypeFormGroup {
    const truckTypeRawValue = {
      ...this.getFormDefaults(),
      ...truckType,
    };
    return new FormGroup<TruckTypeFormGroupContent>({
      id: new FormControl(
        { value: truckTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(truckTypeRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(truckTypeRawValue.name, {
        validators: [Validators.required],
      }),
      category: new FormControl(truckTypeRawValue.category),
      height: new FormControl(truckTypeRawValue.height),
      length: new FormControl(truckTypeRawValue.length),
      maxSpeed: new FormControl(truckTypeRawValue.maxSpeed),
      type: new FormControl(truckTypeRawValue.type),
      weight: new FormControl(truckTypeRawValue.weight),
      width: new FormControl(truckTypeRawValue.width),
    });
  }

  getTruckType(form: TruckTypeFormGroup): ITruckType | NewTruckType {
    return form.getRawValue() as ITruckType | NewTruckType;
  }

  resetForm(form: TruckTypeFormGroup, truckType: TruckTypeFormGroupInput): void {
    const truckTypeRawValue = { ...this.getFormDefaults(), ...truckType };
    form.reset(
      {
        ...truckTypeRawValue,
        id: { value: truckTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TruckTypeFormDefaults {
    return {
      id: null,
    };
  }
}
