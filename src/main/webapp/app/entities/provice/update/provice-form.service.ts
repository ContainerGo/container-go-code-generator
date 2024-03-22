import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProvice, NewProvice } from '../provice.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProvice for edit and NewProviceFormGroupInput for create.
 */
type ProviceFormGroupInput = IProvice | PartialWithRequiredKeyOf<NewProvice>;

type ProviceFormDefaults = Pick<NewProvice, 'id'>;

type ProviceFormGroupContent = {
  id: FormControl<IProvice['id'] | NewProvice['id']>;
  code: FormControl<IProvice['code']>;
  name: FormControl<IProvice['name']>;
  description: FormControl<IProvice['description']>;
};

export type ProviceFormGroup = FormGroup<ProviceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProviceFormService {
  createProviceFormGroup(provice: ProviceFormGroupInput = { id: null }): ProviceFormGroup {
    const proviceRawValue = {
      ...this.getFormDefaults(),
      ...provice,
    };
    return new FormGroup<ProviceFormGroupContent>({
      id: new FormControl(
        { value: proviceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(proviceRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(proviceRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(proviceRawValue.description),
    });
  }

  getProvice(form: ProviceFormGroup): IProvice | NewProvice {
    return form.getRawValue() as IProvice | NewProvice;
  }

  resetForm(form: ProviceFormGroup, provice: ProviceFormGroupInput): void {
    const proviceRawValue = { ...this.getFormDefaults(), ...provice };
    form.reset(
      {
        ...proviceRawValue,
        id: { value: proviceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProviceFormDefaults {
    return {
      id: null,
    };
  }
}
