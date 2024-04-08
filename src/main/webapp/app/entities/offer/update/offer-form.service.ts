import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOffer, NewOffer } from '../offer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOffer for edit and NewOfferFormGroupInput for create.
 */
type OfferFormGroupInput = IOffer | PartialWithRequiredKeyOf<NewOffer>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOffer | NewOffer> = Omit<
  T,
  'estimatedPickupFromDate' | 'estimatedPickupUntilDate' | 'estimatedDropoffFromDate' | 'estimatedDropoffUntilDate'
> & {
  estimatedPickupFromDate?: string | null;
  estimatedPickupUntilDate?: string | null;
  estimatedDropoffFromDate?: string | null;
  estimatedDropoffUntilDate?: string | null;
};

type OfferFormRawValue = FormValueOf<IOffer>;

type NewOfferFormRawValue = FormValueOf<NewOffer>;

type OfferFormDefaults = Pick<
  NewOffer,
  'id' | 'estimatedPickupFromDate' | 'estimatedPickupUntilDate' | 'estimatedDropoffFromDate' | 'estimatedDropoffUntilDate'
>;

type OfferFormGroupContent = {
  id: FormControl<OfferFormRawValue['id'] | NewOffer['id']>;
  message: FormControl<OfferFormRawValue['message']>;
  estimatedPickupFromDate: FormControl<OfferFormRawValue['estimatedPickupFromDate']>;
  estimatedPickupUntilDate: FormControl<OfferFormRawValue['estimatedPickupUntilDate']>;
  estimatedDropoffFromDate: FormControl<OfferFormRawValue['estimatedDropoffFromDate']>;
  estimatedDropoffUntilDate: FormControl<OfferFormRawValue['estimatedDropoffUntilDate']>;
  state: FormControl<OfferFormRawValue['state']>;
  price: FormControl<OfferFormRawValue['price']>;
  carrierId: FormControl<OfferFormRawValue['carrierId']>;
  truckId: FormControl<OfferFormRawValue['truckId']>;
  container: FormControl<OfferFormRawValue['container']>;
};

export type OfferFormGroup = FormGroup<OfferFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OfferFormService {
  createOfferFormGroup(offer: OfferFormGroupInput = { id: null }): OfferFormGroup {
    const offerRawValue = this.convertOfferToOfferRawValue({
      ...this.getFormDefaults(),
      ...offer,
    });
    return new FormGroup<OfferFormGroupContent>({
      id: new FormControl(
        { value: offerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      message: new FormControl(offerRawValue.message),
      estimatedPickupFromDate: new FormControl(offerRawValue.estimatedPickupFromDate, {
        validators: [Validators.required],
      }),
      estimatedPickupUntilDate: new FormControl(offerRawValue.estimatedPickupUntilDate, {
        validators: [Validators.required],
      }),
      estimatedDropoffFromDate: new FormControl(offerRawValue.estimatedDropoffFromDate, {
        validators: [Validators.required],
      }),
      estimatedDropoffUntilDate: new FormControl(offerRawValue.estimatedDropoffUntilDate, {
        validators: [Validators.required],
      }),
      state: new FormControl(offerRawValue.state, {
        validators: [Validators.required],
      }),
      price: new FormControl(offerRawValue.price, {
        validators: [Validators.required],
      }),
      carrierId: new FormControl(offerRawValue.carrierId, {
        validators: [Validators.required],
      }),
      truckId: new FormControl(offerRawValue.truckId),
      container: new FormControl(offerRawValue.container, {
        validators: [Validators.required],
      }),
    });
  }

  getOffer(form: OfferFormGroup): IOffer | NewOffer {
    return this.convertOfferRawValueToOffer(form.getRawValue() as OfferFormRawValue | NewOfferFormRawValue);
  }

  resetForm(form: OfferFormGroup, offer: OfferFormGroupInput): void {
    const offerRawValue = this.convertOfferToOfferRawValue({ ...this.getFormDefaults(), ...offer });
    form.reset(
      {
        ...offerRawValue,
        id: { value: offerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OfferFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      estimatedPickupFromDate: currentTime,
      estimatedPickupUntilDate: currentTime,
      estimatedDropoffFromDate: currentTime,
      estimatedDropoffUntilDate: currentTime,
    };
  }

  private convertOfferRawValueToOffer(rawOffer: OfferFormRawValue | NewOfferFormRawValue): IOffer | NewOffer {
    return {
      ...rawOffer,
      estimatedPickupFromDate: dayjs(rawOffer.estimatedPickupFromDate, DATE_TIME_FORMAT),
      estimatedPickupUntilDate: dayjs(rawOffer.estimatedPickupUntilDate, DATE_TIME_FORMAT),
      estimatedDropoffFromDate: dayjs(rawOffer.estimatedDropoffFromDate, DATE_TIME_FORMAT),
      estimatedDropoffUntilDate: dayjs(rawOffer.estimatedDropoffUntilDate, DATE_TIME_FORMAT),
    };
  }

  private convertOfferToOfferRawValue(
    offer: IOffer | (Partial<NewOffer> & OfferFormDefaults),
  ): OfferFormRawValue | PartialWithRequiredKeyOf<NewOfferFormRawValue> {
    return {
      ...offer,
      estimatedPickupFromDate: offer.estimatedPickupFromDate ? offer.estimatedPickupFromDate.format(DATE_TIME_FORMAT) : undefined,
      estimatedPickupUntilDate: offer.estimatedPickupUntilDate ? offer.estimatedPickupUntilDate.format(DATE_TIME_FORMAT) : undefined,
      estimatedDropoffFromDate: offer.estimatedDropoffFromDate ? offer.estimatedDropoffFromDate.format(DATE_TIME_FORMAT) : undefined,
      estimatedDropoffUntilDate: offer.estimatedDropoffUntilDate ? offer.estimatedDropoffUntilDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
