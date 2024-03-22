import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShipperPerson, NewShipperPerson } from '../shipper-person.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipperPerson for edit and NewShipperPersonFormGroupInput for create.
 */
type ShipperPersonFormGroupInput = IShipperPerson | PartialWithRequiredKeyOf<NewShipperPerson>;

type ShipperPersonFormDefaults = Pick<NewShipperPerson, 'id'>;

type ShipperPersonFormGroupContent = {
  id: FormControl<IShipperPerson['id'] | NewShipperPerson['id']>;
  name: FormControl<IShipperPerson['name']>;
  phone: FormControl<IShipperPerson['phone']>;
  email: FormControl<IShipperPerson['email']>;
  address: FormControl<IShipperPerson['address']>;
  shipper: FormControl<IShipperPerson['shipper']>;
};

export type ShipperPersonFormGroup = FormGroup<ShipperPersonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipperPersonFormService {
  createShipperPersonFormGroup(shipperPerson: ShipperPersonFormGroupInput = { id: null }): ShipperPersonFormGroup {
    const shipperPersonRawValue = {
      ...this.getFormDefaults(),
      ...shipperPerson,
    };
    return new FormGroup<ShipperPersonFormGroupContent>({
      id: new FormControl(
        { value: shipperPersonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(shipperPersonRawValue.name, {
        validators: [Validators.required],
      }),
      phone: new FormControl(shipperPersonRawValue.phone, {
        validators: [Validators.required],
      }),
      email: new FormControl(shipperPersonRawValue.email),
      address: new FormControl(shipperPersonRawValue.address),
      shipper: new FormControl(shipperPersonRawValue.shipper),
    });
  }

  getShipperPerson(form: ShipperPersonFormGroup): IShipperPerson | NewShipperPerson {
    return form.getRawValue() as IShipperPerson | NewShipperPerson;
  }

  resetForm(form: ShipperPersonFormGroup, shipperPerson: ShipperPersonFormGroupInput): void {
    const shipperPersonRawValue = { ...this.getFormDefaults(), ...shipperPerson };
    form.reset(
      {
        ...shipperPersonRawValue,
        id: { value: shipperPersonRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ShipperPersonFormDefaults {
    return {
      id: null,
    };
  }
}
