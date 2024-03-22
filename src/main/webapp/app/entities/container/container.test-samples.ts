import dayjs from 'dayjs/esm';

import { IContainer, NewContainer } from './container.model';

export const sampleWithRequiredData: IContainer = {
  id: 31898,
  contNo: 'prime enchanted phew',
  estimatedPrice: 10120.32,
  distance: 25339.26,
  desiredPrice: 10607.59,
  state: 'SHIPMENT_IN_PROGRESS',
  shipperId: 7253,
  totalWeight: 29130.4,
};

export const sampleWithPartialData: IContainer = {
  id: 3827,
  contNo: 'hm shopper whoever',
  estimatedPrice: 12065.72,
  distance: 12699.57,
  desiredPrice: 32395.5,
  additionalRequirements: 'whopping',
  dropoffLng: 11930.84,
  state: 'WAITING_FOR_OFFERS',
  shipperId: 2828,
  totalWeight: 2981.18,
  biddingUntilDate: dayjs('2024-03-21T15:17'),
};

export const sampleWithFullData: IContainer = {
  id: 30691,
  contNo: 'brr merchandise immediately',
  estimatedPrice: 9760.53,
  distance: 24269.68,
  desiredPrice: 27754.64,
  additionalRequirements: 'disgusting brr greatly',
  dropoffContact: 'inform',
  dropoffContactPhone: 'uh-huh aha',
  dropoffAddress: 'apropos avaricious',
  dropoffLat: 27097.43,
  dropoffLng: 6408.04,
  dropoffUntilDate: dayjs('2024-03-22T04:49'),
  state: 'SHIPMENT_POD_APPROVED',
  shipperId: 26671,
  carrierId: 14646,
  totalWeight: 17097.02,
  pickupFromDate: dayjs('2024-03-22T02:28'),
  biddingFromDate: dayjs('2024-03-21T12:23'),
  biddingUntilDate: dayjs('2024-03-21T09:35'),
};

export const sampleWithNewData: NewContainer = {
  contNo: 'likewise before',
  estimatedPrice: 15682.02,
  distance: 15413.31,
  desiredPrice: 15825.84,
  state: 'SHIPMENT_IN_PROGRESS',
  shipperId: 6350,
  totalWeight: 31922.08,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
