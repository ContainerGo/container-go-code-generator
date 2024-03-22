import dayjs from 'dayjs/esm';

import { IOffer, NewOffer } from './offer.model';

export const sampleWithRequiredData: IOffer = {
  id: 23771,
  pickupFromDate: dayjs('2024-03-21T19:25'),
  pickupUntilDate: dayjs('2024-03-21T10:28'),
  dropoffFromDate: dayjs('2024-03-21T12:38'),
  dropoffUntilDate: dayjs('2024-03-21T23:47'),
  price: 4021.8,
  carrierId: 13755,
};

export const sampleWithPartialData: IOffer = {
  id: 12120,
  pickupFromDate: dayjs('2024-03-21T09:46'),
  pickupUntilDate: dayjs('2024-03-21T19:44'),
  dropoffFromDate: dayjs('2024-03-21T19:01'),
  dropoffUntilDate: dayjs('2024-03-21T12:27'),
  price: 22977.8,
  carrierId: 8000,
};

export const sampleWithFullData: IOffer = {
  id: 1715,
  message: 'elastic than restructuring',
  pickupFromDate: dayjs('2024-03-21T20:12'),
  pickupUntilDate: dayjs('2024-03-21T07:39'),
  dropoffFromDate: dayjs('2024-03-21T13:29'),
  dropoffUntilDate: dayjs('2024-03-21T15:56'),
  price: 19918.23,
  carrierId: 12231,
};

export const sampleWithNewData: NewOffer = {
  pickupFromDate: dayjs('2024-03-22T00:52'),
  pickupUntilDate: dayjs('2024-03-21T07:47'),
  dropoffFromDate: dayjs('2024-03-22T01:22'),
  dropoffUntilDate: dayjs('2024-03-21T15:06'),
  price: 24307.16,
  carrierId: 23533,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
