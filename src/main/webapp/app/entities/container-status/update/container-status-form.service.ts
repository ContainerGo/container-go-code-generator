import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContainerStatus, NewContainerStatus } from '../container-status.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContainerStatus for edit and NewContainerStatusFormGroupInput for create.
 */
type ContainerStatusFormGroupInput = IContainerStatus | PartialWithRequiredKeyOf<NewContainerStatus>;

type ContainerStatusFormDefaults = Pick<NewContainerStatus, 'id'>;

type ContainerStatusFormGroupContent = {
  id: FormControl<IContainerStatus['id'] | NewContainerStatus['id']>;
  code: FormControl<IContainerStatus['code']>;
  name: FormControl<IContainerStatus['name']>;
  description: FormControl<IContainerStatus['description']>;
  group: FormControl<IContainerStatus['group']>;
};

export type ContainerStatusFormGroup = FormGroup<ContainerStatusFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContainerStatusFormService {
  createContainerStatusFormGroup(containerStatus: ContainerStatusFormGroupInput = { id: null }): ContainerStatusFormGroup {
    const containerStatusRawValue = {
      ...this.getFormDefaults(),
      ...containerStatus,
    };
    return new FormGroup<ContainerStatusFormGroupContent>({
      id: new FormControl(
        { value: containerStatusRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(containerStatusRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(containerStatusRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(containerStatusRawValue.description),
      group: new FormControl(containerStatusRawValue.group),
    });
  }

  getContainerStatus(form: ContainerStatusFormGroup): IContainerStatus | NewContainerStatus {
    return form.getRawValue() as IContainerStatus | NewContainerStatus;
  }

  resetForm(form: ContainerStatusFormGroup, containerStatus: ContainerStatusFormGroupInput): void {
    const containerStatusRawValue = { ...this.getFormDefaults(), ...containerStatus };
    form.reset(
      {
        ...containerStatusRawValue,
        id: { value: containerStatusRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContainerStatusFormDefaults {
    return {
      id: null,
    };
  }
}
