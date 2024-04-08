import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShipperPersonGroup, NewShipperPersonGroup } from '../shipper-person-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipperPersonGroup for edit and NewShipperPersonGroupFormGroupInput for create.
 */
type ShipperPersonGroupFormGroupInput = IShipperPersonGroup | PartialWithRequiredKeyOf<NewShipperPersonGroup>;

type ShipperPersonGroupFormDefaults = Pick<NewShipperPersonGroup, 'id'>;

type ShipperPersonGroupFormGroupContent = {
  id: FormControl<IShipperPersonGroup['id'] | NewShipperPersonGroup['id']>;
  name: FormControl<IShipperPersonGroup['name']>;
};

export type ShipperPersonGroupFormGroup = FormGroup<ShipperPersonGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipperPersonGroupFormService {
  createShipperPersonGroupFormGroup(shipperPersonGroup: ShipperPersonGroupFormGroupInput = { id: null }): ShipperPersonGroupFormGroup {
    const shipperPersonGroupRawValue = {
      ...this.getFormDefaults(),
      ...shipperPersonGroup,
    };
    return new FormGroup<ShipperPersonGroupFormGroupContent>({
      id: new FormControl(
        { value: shipperPersonGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(shipperPersonGroupRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getShipperPersonGroup(form: ShipperPersonGroupFormGroup): IShipperPersonGroup | NewShipperPersonGroup {
    return form.getRawValue() as IShipperPersonGroup | NewShipperPersonGroup;
  }

  resetForm(form: ShipperPersonGroupFormGroup, shipperPersonGroup: ShipperPersonGroupFormGroupInput): void {
    const shipperPersonGroupRawValue = { ...this.getFormDefaults(), ...shipperPersonGroup };
    form.reset(
      {
        ...shipperPersonGroupRawValue,
        id: { value: shipperPersonGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipperPersonGroupFormDefaults {
    return {
      id: null,
    };
  }
}
