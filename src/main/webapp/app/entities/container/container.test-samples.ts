import dayjs from 'dayjs/esm';

import { IContainer, NewContainer } from './container.model';

export const sampleWithRequiredData: IContainer = {
  id: 9316,
  contNo: 'because of collector',
  estimatedPrice: 19571.92,
  distance: 15194.53,
  desiredPrice: 18881.02,
  state: 'WAITING_FOR_OFFERS',
  shipperId: 14880,
  totalWeight: 7838.15,
};

export const sampleWithPartialData: IContainer = {
  id: 27738,
  contNo: 'bail rescue',
  estimatedPrice: 10193.46,
  distance: 13367.06,
  desiredPrice: 30759.91,
  additionalRequirements: 'boo',
  dropoffUntilDate: dayjs('2024-03-21T12:27'),
  state: 'WAITING_FOR_OFFERS',
  shipperId: 7,
  totalWeight: 12683.92,
  biddingFromDate: dayjs('2024-03-21T11:44'),
  biddingUntilDate: dayjs('2024-03-21T11:37'),
};

export const sampleWithFullData: IContainer = {
  id: 20831,
  contNo: 'dark',
  estimatedPrice: 21585.91,
  distance: 7052.82,
  desiredPrice: 8268.43,
  additionalRequirements: 'er once houseboat',
  dropoffContact: 'but converse',
  dropoffContactPhone: 'tenderise cautious after',
  dropoffAddress: 'hastily encash',
  dropoffLat: 13140.32,
  dropoffLng: 16157.37,
  dropoffUntilDate: dayjs('2024-03-21T19:57'),
  state: 'BIDDING',
  shipperId: 30303,
  carrierId: 2674,
  totalWeight: 13583.54,
  pickupFromDate: dayjs('2024-03-22T01:51'),
  biddingFromDate: dayjs('2024-03-21T19:06'),
  biddingUntilDate: dayjs('2024-03-22T03:33'),
};

export const sampleWithNewData: NewContainer = {
  contNo: 'liberate while instead',
  estimatedPrice: 25260.82,
  distance: 19923.4,
  desiredPrice: 27340.94,
  state: 'OFFER_CHOSEN',
  shipperId: 8831,
  totalWeight: 27862.83,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
