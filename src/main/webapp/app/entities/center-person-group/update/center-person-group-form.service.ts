import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICenterPersonGroup, NewCenterPersonGroup } from '../center-person-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICenterPersonGroup for edit and NewCenterPersonGroupFormGroupInput for create.
 */
type CenterPersonGroupFormGroupInput = ICenterPersonGroup | PartialWithRequiredKeyOf<NewCenterPersonGroup>;

type CenterPersonGroupFormDefaults = Pick<NewCenterPersonGroup, 'id'>;

type CenterPersonGroupFormGroupContent = {
  id: FormControl<ICenterPersonGroup['id'] | NewCenterPersonGroup['id']>;
  name: FormControl<ICenterPersonGroup['name']>;
};

export type CenterPersonGroupFormGroup = FormGroup<CenterPersonGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CenterPersonGroupFormService {
  createCenterPersonGroupFormGroup(centerPersonGroup: CenterPersonGroupFormGroupInput = { id: null }): CenterPersonGroupFormGroup {
    const centerPersonGroupRawValue = {
      ...this.getFormDefaults(),
      ...centerPersonGroup,
    };
    return new FormGroup<CenterPersonGroupFormGroupContent>({
      id: new FormControl(
        { value: centerPersonGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(centerPersonGroupRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getCenterPersonGroup(form: CenterPersonGroupFormGroup): ICenterPersonGroup | NewCenterPersonGroup {
    return form.getRawValue() as ICenterPersonGroup | NewCenterPersonGroup;
  }

  resetForm(form: CenterPersonGroupFormGroup, centerPersonGroup: CenterPersonGroupFormGroupInput): void {
    const centerPersonGroupRawValue = { ...this.getFormDefaults(), ...centerPersonGroup };
    form.reset(
      {
        ...centerPersonGroupRawValue,
        id: { value: centerPersonGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CenterPersonGroupFormDefaults {
    return {
      id: null,
    };
  }
}
