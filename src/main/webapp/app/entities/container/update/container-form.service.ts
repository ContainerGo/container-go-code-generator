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
  'dropoffUntilDate' | 'pickupFromDate' | 'biddingFromDate' | 'biddingUntilDate'
> & {
  dropoffUntilDate?: string | null;
  pickupFromDate?: string | null;
  biddingFromDate?: string | null;
  biddingUntilDate?: string | null;
};

type ContainerFormRawValue = FormValueOf<IContainer>;

type NewContainerFormRawValue = FormValueOf<NewContainer>;

type ContainerFormDefaults = Pick<NewContainer, 'id' | 'dropoffUntilDate' | 'pickupFromDate' | 'biddingFromDate' | 'biddingUntilDate'>;

type ContainerFormGroupContent = {
  id: FormControl<ContainerFormRawValue['id'] | NewContainer['id']>;
  contNo: FormControl<ContainerFormRawValue['contNo']>;
  estimatedPrice: FormControl<ContainerFormRawValue['estimatedPrice']>;
  distance: FormControl<ContainerFormRawValue['distance']>;
  desiredPrice: FormControl<ContainerFormRawValue['desiredPrice']>;
  additionalRequirements: FormControl<ContainerFormRawValue['additionalRequirements']>;
  dropoffContact: FormControl<ContainerFormRawValue['dropoffContact']>;
  dropoffContactPhone: FormControl<ContainerFormRawValue['dropoffContactPhone']>;
  dropoffAddress: FormControl<ContainerFormRawValue['dropoffAddress']>;
  dropoffLat: FormControl<ContainerFormRawValue['dropoffLat']>;
  dropoffLng: FormControl<ContainerFormRawValue['dropoffLng']>;
  dropoffUntilDate: FormControl<ContainerFormRawValue['dropoffUntilDate']>;
  state: FormControl<ContainerFormRawValue['state']>;
  shipperId: FormControl<ContainerFormRawValue['shipperId']>;
  carrierId: FormControl<ContainerFormRawValue['carrierId']>;
  totalWeight: FormControl<ContainerFormRawValue['totalWeight']>;
  pickupFromDate: FormControl<ContainerFormRawValue['pickupFromDate']>;
  biddingFromDate: FormControl<ContainerFormRawValue['biddingFromDate']>;
  biddingUntilDate: FormControl<ContainerFormRawValue['biddingUntilDate']>;
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
      dropoffContact: new FormControl(containerRawValue.dropoffContact),
      dropoffContactPhone: new FormControl(containerRawValue.dropoffContactPhone),
      dropoffAddress: new FormControl(containerRawValue.dropoffAddress),
      dropoffLat: new FormControl(containerRawValue.dropoffLat),
      dropoffLng: new FormControl(containerRawValue.dropoffLng),
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
      pickupFromDate: new FormControl(containerRawValue.pickupFromDate),
      biddingFromDate: new FormControl(containerRawValue.biddingFromDate),
      biddingUntilDate: new FormControl(containerRawValue.biddingUntilDate),
      dropoffProvice: new FormControl(containerRawValue.dropoffProvice),
      dropoffDistrict: new FormControl(containerRawValue.dropoffDistrict),
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
      dropoffUntilDate: currentTime,
      pickupFromDate: currentTime,
      biddingFromDate: currentTime,
      biddingUntilDate: currentTime,
    };
  }

  private convertContainerRawValueToContainer(rawContainer: ContainerFormRawValue | NewContainerFormRawValue): IContainer | NewContainer {
    return {
      ...rawContainer,
      dropoffUntilDate: dayjs(rawContainer.dropoffUntilDate, DATE_TIME_FORMAT),
      pickupFromDate: dayjs(rawContainer.pickupFromDate, DATE_TIME_FORMAT),
      biddingFromDate: dayjs(rawContainer.biddingFromDate, DATE_TIME_FORMAT),
      biddingUntilDate: dayjs(rawContainer.biddingUntilDate, DATE_TIME_FORMAT),
    };
  }

  private convertContainerToContainerRawValue(
    container: IContainer | (Partial<NewContainer> & ContainerFormDefaults),
  ): ContainerFormRawValue | PartialWithRequiredKeyOf<NewContainerFormRawValue> {
    return {
      ...container,
      dropoffUntilDate: container.dropoffUntilDate ? container.dropoffUntilDate.format(DATE_TIME_FORMAT) : undefined,
      pickupFromDate: container.pickupFromDate ? container.pickupFromDate.format(DATE_TIME_FORMAT) : undefined,
      biddingFromDate: container.biddingFromDate ? container.biddingFromDate.format(DATE_TIME_FORMAT) : undefined,
      biddingUntilDate: container.biddingUntilDate ? container.biddingUntilDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
