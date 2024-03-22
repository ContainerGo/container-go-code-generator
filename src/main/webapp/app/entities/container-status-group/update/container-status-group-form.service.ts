import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContainerStatusGroup, NewContainerStatusGroup } from '../container-status-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContainerStatusGroup for edit and NewContainerStatusGroupFormGroupInput for create.
 */
type ContainerStatusGroupFormGroupInput = IContainerStatusGroup | PartialWithRequiredKeyOf<NewContainerStatusGroup>;

type ContainerStatusGroupFormDefaults = Pick<NewContainerStatusGroup, 'id'>;

type ContainerStatusGroupFormGroupContent = {
  id: FormControl<IContainerStatusGroup['id'] | NewContainerStatusGroup['id']>;
  code: FormControl<IContainerStatusGroup['code']>;
  name: FormControl<IContainerStatusGroup['name']>;
  description: FormControl<IContainerStatusGroup['description']>;
};

export type ContainerStatusGroupFormGroup = FormGroup<ContainerStatusGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContainerStatusGroupFormService {
  createContainerStatusGroupFormGroup(
    containerStatusGroup: ContainerStatusGroupFormGroupInput = { id: null },
  ): ContainerStatusGroupFormGroup {
    const containerStatusGroupRawValue = {
      ...this.getFormDefaults(),
      ...containerStatusGroup,
    };
    return new FormGroup<ContainerStatusGroupFormGroupContent>({
      id: new FormControl(
        { value: containerStatusGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(containerStatusGroupRawValue.code, {
        validators: [Validators.required],
      }),
      name: new FormControl(containerStatusGroupRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(containerStatusGroupRawValue.description),
    });
  }

  getContainerStatusGroup(form: ContainerStatusGroupFormGroup): IContainerStatusGroup | NewContainerStatusGroup {
    return form.getRawValue() as IContainerStatusGroup | NewContainerStatusGroup;
  }

  resetForm(form: ContainerStatusGroupFormGroup, containerStatusGroup: ContainerStatusGroupFormGroupInput): void {
    const containerStatusGroupRawValue = { ...this.getFormDefaults(), ...containerStatusGroup };
    form.reset(
      {
        ...containerStatusGroupRawValue,
        id: { value: containerStatusGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContainerStatusGroupFormDefaults {
    return {
      id: null,
    };
  }
}
