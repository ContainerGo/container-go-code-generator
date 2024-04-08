import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICenterPerson, NewCenterPerson } from '../center-person.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICenterPerson for edit and NewCenterPersonFormGroupInput for create.
 */
type CenterPersonFormGroupInput = ICenterPerson | PartialWithRequiredKeyOf<NewCenterPerson>;

type CenterPersonFormDefaults = Pick<NewCenterPerson, 'id' | 'groups'>;

type CenterPersonFormGroupContent = {
  id: FormControl<ICenterPerson['id'] | NewCenterPerson['id']>;
  name: FormControl<ICenterPerson['name']>;
  phone: FormControl<ICenterPerson['phone']>;
  email: FormControl<ICenterPerson['email']>;
  address: FormControl<ICenterPerson['address']>;
  group: FormControl<ICenterPerson['group']>;
  groups: FormControl<ICenterPerson['groups']>;
};

export type CenterPersonFormGroup = FormGroup<CenterPersonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CenterPersonFormService {
  createCenterPersonFormGroup(centerPerson: CenterPersonFormGroupInput = { id: null }): CenterPersonFormGroup {
    const centerPersonRawValue = {
      ...this.getFormDefaults(),
      ...centerPerson,
    };
    return new FormGroup<CenterPersonFormGroupContent>({
      id: new FormControl(
        { value: centerPersonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(centerPersonRawValue.name, {
        validators: [Validators.required],
      }),
      phone: new FormControl(centerPersonRawValue.phone, {
        validators: [Validators.required],
      }),
      email: new FormControl(centerPersonRawValue.email),
      address: new FormControl(centerPersonRawValue.address),
      group: new FormControl(centerPersonRawValue.group, {
        validators: [Validators.required],
      }),
      groups: new FormControl(centerPersonRawValue.groups ?? []),
    });
  }

  getCenterPerson(form: CenterPersonFormGroup): ICenterPerson | NewCenterPerson {
    return form.getRawValue() as ICenterPerson | NewCenterPerson;
  }

  resetForm(form: CenterPersonFormGroup, centerPerson: CenterPersonFormGroupInput): void {
    const centerPersonRawValue = { ...this.getFormDefaults(), ...centerPerson };
    form.reset(
      {
        ...centerPersonRawValue,
        id: { value: centerPersonRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CenterPersonFormDefaults {
    return {
      id: null,
      groups: [],
    };
  }
}
