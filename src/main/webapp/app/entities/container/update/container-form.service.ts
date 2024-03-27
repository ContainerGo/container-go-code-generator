import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContainer, NewContainer } from '../container.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContainer for edit and NewContainerFormGroupInput for create.
 */
type ContainerFormGroupInput = IContainer | PartialWithRequiredKeyOf<NewContainer>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContainer | NewContainer> = Omit<
  T,
  'pickupFromDate' | 'dropoffUntilDate' | 'biddingFromDate' | 'biddingUntilDate'
> & {
  pickupFromDate?: string | null;
  dropoffUntilDate?: string | null;
  biddingFromDate?: string | null;
  biddingUntilDate?: string | null;
};

type ContainerFormRawValue = FormValueOf<IContainer>;

type NewContainerFormRawValue = FormValueOf<NewContainer>;

type ContainerFormDefaults = Pick<NewContainer, 'id' | 'pickupFromDate' | 'dropoffUntilDate' | 'biddingFromDate' | 'biddingUntilDate'>;

type ContainerFormGroupContent = {
  id: FormControl<ContainerFormRawValue['id'] | NewContainer['id']>;
  contNo: FormControl<ContainerFormRawValue['contNo']>;
  estimatedPrice: FormControl<ContainerFormRawValue['estimatedPrice']>;
  distance: FormControl<ContainerFormRawValue['distance']>;
  desiredPrice: FormControl<ContainerFormRawValue['desiredPrice']>;
  additionalRequirements: FormControl<ContainerFormRawValue['additionalRequirements']>;
  pickupContact: FormControl<ContainerFormRawValue['pickupContact']>;
  pickupContactPhone: FormControl<ContainerFormRawValue['pickupContactPhone']>;
  pickupAddress: FormControl<ContainerFormRawValue['pickupAddress']>;
  pickupLat: FormControl<ContainerFormRawValue['pickupLat']>;
  pickupLng: FormControl<ContainerFormRawValue['pickupLng']>;
  pickupFromDate: FormControl<ContainerFormRawValue['pickupFromDate']>;
  dropoffContact: FormControl<ContainerFormRawValue['dropoffContact']>;
  dropoffContactPhone: FormControl<ContainerFormRawValue['dropoffContactPhone']>;
  dropoffAddress: FormControl<ContainerFormRawValue['dropoffAddress']>;
  dropoffLat: FormControl<ContainerFormRawValue['dropoffLat']>;
  dropoffLng: FormControl<ContainerFormRawValue['dropoffLng']>;
  points: FormControl<ContainerFormRawValue['points']>;
  dropoffUntilDate: FormControl<ContainerFormRawValue['dropoffUntilDate']>;
  state: FormControl<ContainerFormRawValue['state']>;
  shipperId: FormControl<ContainerFormRawValue['shipperId']>;
  carrierId: FormControl<ContainerFormRawValue['carrierId']>;
  totalWeight: FormControl<ContainerFormRawValue['totalWeight']>;
  biddingFromDate: FormControl<ContainerFormRawValue['biddingFromDate']>;
  biddingUntilDate: FormControl<ContainerFormRawValue['biddingUntilDate']>;
  pickupProvice: FormControl<ContainerFormRawValue['pickupProvice']>;
  pickupDistrict: FormControl<ContainerFormRawValue['pickupDistrict']>;
  pickupWard: FormControl<ContainerFormRawValue['pickupWard']>;
  dropoffProvice: FormControl<ContainerFormRawValue['dropoffProvice']>;
  dropoffDistrict: FormControl<ContainerFormRawValue['dropoffDistrict']>;
  dropoffWard: FormControl<ContainerFormRawValue['dropoffWard']>;
  type: FormControl<ContainerFormRawValue['type']>;
  status: FormControl<ContainerFormRawValue['status']>;
  truckType: FormControl<ContainerFormRawValue['truckType']>;
  truck: FormControl<ContainerFormRawValue['truck']>;
  owner: FormControl<ContainerFormRawValue['owner']>;
};

export type ContainerFormGroup = FormGroup<ContainerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContainerFormService {
  createContainerFormGroup(container: ContainerFormGroupInput = { id: null }): ContainerFormGroup {
    const containerRawValue = this.convertContainerToContainerRawValue({
      ...this.getFormDefaults(),
      ...container,
    });
    return new FormGroup<ContainerFormGroupContent>({
      id: new FormControl(
        { value: containerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      contNo: new FormControl(containerRawValue.contNo, {
        validators: [Validators.required],
      }),
      estimatedPrice: new FormControl(containerRawValue.estimatedPrice, {
        validators: [Validators.required],
      }),
      distance: new FormControl(containerRawValue.distance, {
        validators: [Validators.required],
      }),
      desiredPrice: new FormControl(containerRawValue.desiredPrice, {
        validators: [Validators.required],
      }),
      additionalRequirements: new FormControl(containerRawValue.additionalRequirements),
      pickupContact: new FormControl(containerRawValue.pickupContact, {
        validators: [Validators.required],
      }),
      pickupContactPhone: new FormControl(containerRawValue.pickupContactPhone, {
        validators: [Validators.required],
      }),
      pickupAddress: new FormControl(containerRawValue.pickupAddress, {
        validators: [Validators.required],
      }),
      pickupLat: new FormControl(containerRawValue.pickupLat, {
        validators: [Validators.required],
      }),
      pickupLng: new FormControl(containerRawValue.pickupLng, {
        validators: [Validators.required],
      }),
      pickupFromDate: new FormControl(containerRawValue.pickupFromDate, {
        validators: [Validators.required],
      }),
      dropoffContact: new FormControl(containerRawValue.dropoffContact),
      dropoffContactPhone: new FormControl(containerRawValue.dropoffContactPhone),
      dropoffAddress: new FormControl(containerRawValue.dropoffAddress, {
        validators: [Validators.required],
      }),
      dropoffLat: new FormControl(containerRawValue.dropoffLat),
      dropoffLng: new FormControl(containerRawValue.dropoffLng),
      points: new FormControl(containerRawValue.points),
      dropoffUntilDate: new FormControl(containerRawValue.dropoffUntilDate),
      state: new FormControl(containerRawValue.state, {
        validators: [Validators.required],
      }),
      shipperId: new FormControl(containerRawValue.shipperId, {
        validators: [Validators.required],
      }),
      carrierId: new FormControl(containerRawValue.carrierId),
      totalWeight: new FormControl(containerRawValue.totalWeight, {
        validators: [Validators.required],
      }),
      biddingFromDate: new FormControl(containerRawValue.biddingFromDate),
      biddingUntilDate: new FormControl(containerRawValue.biddingUntilDate),
      pickupProvice: new FormControl(containerRawValue.pickupProvice, {
        validators: [Validators.required],
      }),
      pickupDistrict: new FormControl(containerRawValue.pickupDistrict, {
        validators: [Validators.required],
      }),
      pickupWard: new FormControl(containerRawValue.pickupWard, {
        validators: [Validators.required],
      }),
      dropoffProvice: new FormControl(containerRawValue.dropoffProvice, {
        validators: [Validators.required],
      }),
      dropoffDistrict: new FormControl(containerRawValue.dropoffDistrict, {
        validators: [Validators.required],
      }),
      dropoffWard: new FormControl(containerRawValue.dropoffWard),
      type: new FormControl(containerRawValue.type, {
        validators: [Validators.required],
      }),
      status: new FormControl(containerRawValue.status, {
        validators: [Validators.required],
      }),
      truckType: new FormControl(containerRawValue.truckType),
      truck: new FormControl(containerRawValue.truck),
      owner: new FormControl(containerRawValue.owner),
    });
  }

  getContainer(form: ContainerFormGroup): IContainer | NewContainer {
    return this.convertContainerRawValueToContainer(form.getRawValue() as ContainerFormRawValue | NewContainerFormRawValue);
  }

  resetForm(form: ContainerFormGroup, container: ContainerFormGroupInput): void {
    const containerRawValue = this.convertContainerToContainerRawValue({ ...this.getFormDefaults(), ...container });
    form.reset(
      {
        ...containerRawValue,
        id: { value: containerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContainerFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      pickupFromDate: currentTime,
      dropoffUntilDate: currentTime,
      biddingFromDate: currentTime,
      biddingUntilDate: currentTime,
    };
  }

  private convertContainerRawValueToContainer(rawContainer: ContainerFormRawValue | NewContainerFormRawValue): IContainer | NewContainer {
    return {
      ...rawContainer,
      pickupFromDate: dayjs(rawContainer.pickupFromDate, DATE_TIME_FORMAT),
      dropoffUntilDate: dayjs(rawContainer.dropoffUntilDate, DATE_TIME_FORMAT),
      biddingFromDate: dayjs(rawContainer.biddingFromDate, DATE_TIME_FORMAT),
      biddingUntilDate: dayjs(rawContainer.biddingUntilDate, DATE_TIME_FORMAT),
    };
  }

  private convertContainerToContainerRawValue(
    container: IContainer | (Partial<NewContainer> & ContainerFormDefaults),
  ): ContainerFormRawValue | PartialWithRequiredKeyOf<NewContainerFormRawValue> {
    return {
      ...container,
      pickupFromDate: container.pickupFromDate ? container.pickupFromDate.format(DATE_TIME_FORMAT) : undefined,
      dropoffUntilDate: container.dropoffUntilDate ? container.dropoffUntilDate.format(DATE_TIME_FORMAT) : undefined,
      biddingFromDate: container.biddingFromDate ? container.biddingFromDate.format(DATE_TIME_FORMAT) : undefined,
      biddingUntilDate: container.biddingUntilDate ? container.biddingUntilDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
