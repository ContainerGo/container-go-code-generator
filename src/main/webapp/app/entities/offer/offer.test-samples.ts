import dayjs from 'dayjs/esm';

import { IOffer, NewOffer } from './offer.model';

export const sampleWithRequiredData: IOffer = {
  id: 6332,
  pickupFromDate: dayjs('2024-03-21T08:19'),
  pickupUntilDate: dayjs('2024-03-21T13:49'),
  dropoffFromDate: dayjs('2024-03-21T15:47'),
  dropoffUntilDate: dayjs('2024-03-21T11:56'),
  state: 'PENDING',
  price: 8253.28,
  carrierId: 15481,
};

export const sampleWithPartialData: IOffer = {
  id: 55,
  pickupFromDate: dayjs('2024-03-21T13:53'),
  pickupUntilDate: dayjs('2024-03-21T18:08'),
  dropoffFromDate: dayjs('2024-03-21T17:39'),
  dropoffUntilDate: dayjs('2024-03-22T06:12'),
  state: 'ACCEPTED',
  price: 28503.82,
  carrierId: 32052,
  truckId: 30876,
};

export const sampleWithFullData: IOffer = {
  id: 14224,
  message: 'peach gracefully',
  pickupFromDate: dayjs('2024-03-21T18:31'),
  pickupUntilDate: dayjs('2024-03-21T16:24'),
  dropoffFromDate: dayjs('2024-03-22T04:33'),
  dropoffUntilDate: dayjs('2024-03-22T02:05'),
  state: 'PENDING',
  price: 1561.64,
  carrierId: 3839,
  carrierPersonId: 7112,
  truckId: 23184,
};

export const sampleWithNewData: NewOffer = {
  pickupFromDate: dayjs('2024-03-21T12:50'),
  pickupUntilDate: dayjs('2024-03-21T21:06'),
  dropoffFromDate: dayjs('2024-03-21T08:45'),
  dropoffUntilDate: dayjs('2024-03-22T02:11'),
  state: 'EXPIRED',
  price: 9451.43,
  carrierId: 20954,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
