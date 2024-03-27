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
type FormValueOf<T extends IOffer | NewOffer> = Omit<T, 'pickupFromDate' | 'pickupUntilDate' | 'dropoffFromDate' | 'dropoffUntilDate'> & {
  pickupFromDate?: string | null;
  pickupUntilDate?: string | null;
  dropoffFromDate?: string | null;
  dropoffUntilDate?: string | null;
};

type OfferFormRawValue = FormValueOf<IOffer>;

type NewOfferFormRawValue = FormValueOf<NewOffer>;

type OfferFormDefaults = Pick<NewOffer, 'id' | 'pickupFromDate' | 'pickupUntilDate' | 'dropoffFromDate' | 'dropoffUntilDate'>;

type OfferFormGroupContent = {
  id: FormControl<OfferFormRawValue['id'] | NewOffer['id']>;
  message: FormControl<OfferFormRawValue['message']>;
  pickupFromDate: FormControl<OfferFormRawValue['pickupFromDate']>;
  pickupUntilDate: FormControl<OfferFormRawValue['pickupUntilDate']>;
  dropoffFromDate: FormControl<OfferFormRawValue['dropoffFromDate']>;
  dropoffUntilDate: FormControl<OfferFormRawValue['dropoffUntilDate']>;
  state: FormControl<OfferFormRawValue['state']>;
  price: FormControl<OfferFormRawValue['price']>;
  carrierId: FormControl<OfferFormRawValue['carrierId']>;
  carrierPersonId: FormControl<OfferFormRawValue['carrierPersonId']>;
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
      pickupFromDate: new FormControl(offerRawValue.pickupFromDate, {
        validators: [Validators.required],
      }),
      pickupUntilDate: new FormControl(offerRawValue.pickupUntilDate, {
        validators: [Validators.required],
      }),
      dropoffFromDate: new FormControl(offerRawValue.dropoffFromDate, {
        validators: [Validators.required],
      }),
      dropoffUntilDate: new FormControl(offerRawValue.dropoffUntilDate, {
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
      carrierPersonId: new FormControl(offerRawValue.carrierPersonId),
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
      pickupFromDate: currentTime,
      pickupUntilDate: currentTime,
      dropoffFromDate: currentTime,
      dropoffUntilDate: currentTime,
    };
  }

  private convertOfferRawValueToOffer(rawOffer: OfferFormRawValue | NewOfferFormRawValue): IOffer | NewOffer {
    return {
      ...rawOffer,
      pickupFromDate: dayjs(rawOffer.pickupFromDate, DATE_TIME_FORMAT),
      pickupUntilDate: dayjs(rawOffer.pickupUntilDate, DATE_TIME_FORMAT),
      dropoffFromDate: dayjs(rawOffer.dropoffFromDate, DATE_TIME_FORMAT),
      dropoffUntilDate: dayjs(rawOffer.dropoffUntilDate, DATE_TIME_FORMAT),
    };
  }

  private convertOfferToOfferRawValue(
    offer: IOffer | (Partial<NewOffer> & OfferFormDefaults),
  ): OfferFormRawValue | PartialWithRequiredKeyOf<NewOfferFormRawValue> {
    return {
      ...offer,
      pickupFromDate: offer.pickupFromDate ? offer.pickupFromDate.format(DATE_TIME_FORMAT) : undefined,
      pickupUntilDate: offer.pickupUntilDate ? offer.pickupUntilDate.format(DATE_TIME_FORMAT) : undefined,
      dropoffFromDate: offer.dropoffFromDate ? offer.dropoffFromDate.format(DATE_TIME_FORMAT) : undefined,
      dropoffUntilDate: offer.dropoffUntilDate ? offer.dropoffUntilDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
