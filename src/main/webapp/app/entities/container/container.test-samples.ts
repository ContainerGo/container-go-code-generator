import dayjs from 'dayjs/esm';

import { IContainer, NewContainer } from './container.model';

export const sampleWithRequiredData: IContainer = {
  id: 17016,
  contNo: 'aw gah',
  estimatedPrice: 5884.82,
  distance: 19571.92,
  desiredPrice: 15194.53,
  pickupContact: 'zowie however',
  pickupContactPhone: 'next geez squint',
  pickupAddress: 'abnormally properly',
  pickupLat: 26645.92,
  pickupLng: 30575.4,
  pickupFromDate: dayjs('2024-03-21T14:52'),
  dropoffAddress: 'outside barring aw',
  state: 'SHIPMENT_IN_PROGRESS',
  shipperId: 14497,
  totalWeight: 2756.11,
};

export const sampleWithPartialData: IContainer = {
  id: 23998,
  contNo: 'although last yearningly',
  estimatedPrice: 28664.18,
  distance: 29393.72,
  desiredPrice: 11375.46,
  pickupContact: 'apud schlepp',
  pickupContactPhone: 'transport',
  pickupAddress: 'ah',
  pickupLat: 18905.84,
  pickupLng: 14766.69,
  pickupFromDate: dayjs('2024-03-22T06:45'),
  dropoffContact: 'exactly nervously stand',
  dropoffContactPhone: 'marshal',
  dropoffAddress: 'spotless',
  dropoffLat: 30739.61,
  points: 'antedate',
  dropoffUntilDate: dayjs('2024-03-22T02:58'),
  state: 'NEW',
  shipperId: 5213,
  carrierId: 30420,
  totalWeight: 14467.38,
  biddingFromDate: dayjs('2024-03-21T13:35'),
  biddingUntilDate: dayjs('2024-03-21T19:36'),
};

export const sampleWithFullData: IContainer = {
  id: 31449,
  contNo: 'how',
  estimatedPrice: 6092.96,
  distance: 19091.86,
  desiredPrice: 22017.25,
  additionalRequirements: 'greedy uh-huh next',
  pickupContact: 'gee doodle',
  pickupContactPhone: 'psst whoever',
  pickupAddress: 'miserably since',
  pickupLat: 14604.57,
  pickupLng: 28705.33,
  pickupFromDate: dayjs('2024-03-21T17:52'),
  dropoffContact: 'apropos abstain',
  dropoffContactPhone: 'gadzooks',
  dropoffAddress: 'roughly unaccountably when',
  dropoffLat: 16237.83,
  dropoffLng: 15371.76,
  points: 'intimidate against whoosh',
  dropoffUntilDate: dayjs('2024-03-21T19:34'),
  state: 'SHIPMENT_IN_PROGRESS',
  shipperId: 31571,
  carrierId: 12381,
  totalWeight: 14873.29,
  biddingFromDate: dayjs('2024-03-22T02:17'),
  biddingUntilDate: dayjs('2024-03-22T04:17'),
};

export const sampleWithNewData: NewContainer = {
  contNo: 'sculptural',
  estimatedPrice: 18252.13,
  distance: 11196.5,
  desiredPrice: 11441.68,
  pickupContact: 'though',
  pickupContactPhone: 'so',
  pickupAddress: 'unaccountably schmooze poleaxe',
  pickupLat: 4798.51,
  pickupLng: 27398.54,
  pickupFromDate: dayjs('2024-03-22T06:45'),
  dropoffAddress: 'aha',
  state: 'SHIPMENT_POD_APPROVED',
  shipperId: 6054,
  totalWeight: 13425.52,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
