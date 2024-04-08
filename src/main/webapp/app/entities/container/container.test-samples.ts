import dayjs from 'dayjs/esm';

import { IContainer, NewContainer } from './container.model';

export const sampleWithRequiredData: IContainer = {
  id: '85116500-8932-44c3-b729-793732ededa1',
  contNo: 'ugh drool',
  estimatedPrice: 24949.96,
  distance: 1924.4,
  desiredPrice: 7751.69,
  pickupContact: 'except abnormally properly',
  pickupContactPhone: 'hence suspiciously marathon',
  pickupAddress: 'houseboat basis',
  pickupLat: 11175.09,
  pickupLng: 23997.9,
  pickupFromDate: dayjs('2024-03-21T13:07'),
  dropoffAddress: 'virtue rarely',
  state: 'OFFER_CHOSEN',
  shipperId: 'edd1fde5-a009-4fc9-b252-521dc3546772',
  totalWeight: 2673.95,
};

export const sampleWithPartialData: IContainer = {
  id: 'd7ba1273-e89d-4408-96c9-d54d5dbab5e8',
  contNo: 'vivid eek',
  estimatedPrice: 971.74,
  distance: 5353.16,
  desiredPrice: 1788.67,
  additionalRequirements: 'work',
  pickupContact: 'playfully',
  pickupContactPhone: 'vague refuge that',
  pickupAddress: 'meaningfully during',
  pickupLat: 8902.94,
  pickupLng: 18876.21,
  pickupFromDate: dayjs('2024-03-21T14:18'),
  dropoffContact: 'send',
  dropoffContactPhone: 'during beanie',
  dropoffAddress: 'monumental crop limited',
  dropoffLat: 132.63,
  dropoffUntilDate: dayjs('2024-03-22T03:27'),
  state: 'SHIPMENT_POD_APPROVED',
  shipperId: '120ccaeb-6de1-42a5-bb00-20dd9b690c77',
  totalWeight: 1699.74,
  biddingFromDate: dayjs('2024-03-21T20:52'),
  biddingUntilDate: dayjs('2024-03-22T01:52'),
};

export const sampleWithFullData: IContainer = {
  id: 'baf7147f-1211-4356-888f-7af673103996',
  contNo: 'furthermore drat',
  estimatedPrice: 29369.16,
  distance: 20723.89,
  desiredPrice: 5251.35,
  additionalRequirements: 'provided bah',
  pickupContact: 'feint',
  pickupContactPhone: 'incidentally apud into',
  pickupAddress: 'roast deem',
  pickupLat: 10535.78,
  pickupLng: 10233.44,
  pickupFromDate: dayjs('2024-03-21T20:43'),
  dropoffContact: 'ah acquit',
  dropoffContactPhone: 'reassuringly',
  dropoffAddress: 'debar smoothly',
  dropoffLat: 19412.2,
  dropoffLng: 5567.66,
  points: 'unmask er leave',
  dropoffUntilDate: dayjs('2024-03-21T17:42'),
  state: 'BIDDING',
  shipperId: '847f31a0-4d32-4043-85fa-c484d92be87f',
  carrierId: '1a76dbd6-0c4f-4620-9c4d-a4fbe4120317',
  totalWeight: 31724.36,
  biddingFromDate: dayjs('2024-03-21T18:43'),
  biddingUntilDate: dayjs('2024-03-21T08:17'),
};

export const sampleWithNewData: NewContainer = {
  contNo: 'traumatic',
  estimatedPrice: 29301.34,
  distance: 7946.14,
  desiredPrice: 1308.65,
  pickupContact: 'shrill yuck',
  pickupContactPhone: 'burly wrench',
  pickupAddress: 'tough digitalise towards',
  pickupLat: 10145.73,
  pickupLng: 28727.27,
  pickupFromDate: dayjs('2024-03-21T16:29'),
  dropoffAddress: 'false boohoo',
  state: 'SHIPMENT_POD_APPROVED',
  shipperId: '6c120aa7-adc0-4df1-b84b-d98abefa55d9',
  totalWeight: 15519.85,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
