import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContainerOwner, NewContainerOwner } from '../container-owner.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContainerOwner for edit and NewContainerOwnerFormGroupInput for create.
 */
type ContainerOwnerFormGroupInput = IContainerOwner | PartialWithRequiredKeyOf<NewContainerOwner>;

type ContainerOwnerFormDefaults = Pick<NewContainerOwner, 'id'>;

type ContainerOwnerFormGroupContent = {
  id: FormControl<IContainerOwner['id'] | NewContainerOwner['id']>;
  name: FormControl<IContainerOwner['name']>;
  phone: FormControl<IContainerOwner['phone']>;
  email: FormControl<IContainerOwner['email']>;
  address: FormControl<IContainerOwner['address']>;
};

export type ContainerOwnerFormGroup = FormGroup<ContainerOwnerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContainerOwnerFormService {
  createContainerOwnerFormGroup(containerOwner: ContainerOwnerFormGroupInput = { id: null }): ContainerOwnerFormGroup {
    const containerOwnerRawValue = {
      ...this.getFormDefaults(),
      ...containerOwner,
    };
    return new FormGroup<ContainerOwnerFormGroupContent>({
      id: new FormControl(
        { value: containerOwnerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(containerOwnerRawValue.name, {
        validators: [Validators.required],
      }),
      phone: new FormControl(containerOwnerRawValue.phone),
      email: new FormControl(containerOwnerRawValue.email),
      address: new FormControl(containerOwnerRawValue.address),
    });
  }

  getContainerOwner(form: ContainerOwnerFormGroup): IContainerOwner | NewContainerOwner {
    return form.getRawValue() as IContainerOwner | NewContainerOwner;
  }

  resetForm(form: ContainerOwnerFormGroup, containerOwner: ContainerOwnerFormGroupInput): void {
    const containerOwnerRawValue = { ...this.getFormDefaults(), ...containerOwner };
    form.reset(
      {
        ...containerOwnerRawValue,
        id: { value: containerOwnerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContainerOwnerFormDefaults {
    return {
      id: null,
    };
  }
}
