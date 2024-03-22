import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContainerType, NewContainerType } from '../container-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContainerType for edit and NewContainerTypeFormGroupInput for create.
 */
type ContainerTypeFormGroupInput = IContainerType | PartialWithRequiredKeyOf<NewContainerType>;

type ContainerTypeFormDefaults = Pick<NewContainerType, 'id'>;

type ContainerTypeFormGroupContent = {
  id: FormControl<IContainerType['id'] | NewContainerType['id']>;
  code: FormControl<IContainerType['code']>;
  name: FormControl<IContainerType['name']>;
  description: FormControl<IContainerType['description']>;
};

export type ContainerTypeFormGroup = FormGroup<ContainerTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContainerTypeFormService {
  createContainerTypeFormGroup(containerType: ContainerTypeFormGroupInput = { id: null }): ContainerTypeFormGroup {
    const containerTypeRawValue = {
      ...this.getFormDefaults(),
      ...containerType,
    };
    return new FormGroup<ContainerTypeFormGroupContent>({
      id: new FormControl(
        { value: containerTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(containerTypeRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(containerTypeRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(containerTypeRawValue.description),
    });
  }

  getContainerType(form: ContainerTypeFormGroup): IContainerType | NewContainerType {
    return form.getRawValue() as IContainerType | NewContainerType;
  }

  resetForm(form: ContainerTypeFormGroup, containerType: ContainerTypeFormGroupInput): void {
    const containerTypeRawValue = { ...this.getFormDefaults(), ...containerType };
    form.reset(
      {
        ...containerTypeRawValue,
        id: { value: containerTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContainerTypeFormDefaults {
    return {
      id: null,
    };
  }
}
