import dayjs from 'dayjs/esm';

import { IOffer, NewOffer } from './offer.model';

export const sampleWithRequiredData: IOffer = {
  id: 'f3fbac24-7b97-40b8-a806-dff68e67275a',
  estimatedPickupFromDate: dayjs('2024-03-21T17:35'),
  estimatedPickupUntilDate: dayjs('2024-03-22T05:00'),
  estimatedDropoffFromDate: dayjs('2024-03-21T09:21'),
  estimatedDropoffUntilDate: dayjs('2024-03-21T07:54'),
  state: 'ACCEPTED',
  price: 9859.66,
  carrierId: '89132013-bc6e-43a4-ba7e-c6c0499bd88c',
};

export const sampleWithPartialData: IOffer = {
  id: 'a82f995d-38a3-4a55-a864-de55f3ca112d',
  estimatedPickupFromDate: dayjs('2024-03-21T17:45'),
  estimatedPickupUntilDate: dayjs('2024-03-21T07:37'),
  estimatedDropoffFromDate: dayjs('2024-03-21T19:13'),
  estimatedDropoffUntilDate: dayjs('2024-03-22T06:24'),
  state: 'EXPIRED',
  price: 18277.36,
  carrierId: '36ca0a6a-7bca-4e2d-8339-fdcf71e9740d',
  truckId: '3e9984c6-e24c-4501-bece-b9db7adccef1',
};

export const sampleWithFullData: IOffer = {
  id: '450111e9-4ec3-4872-bc83-7a21a0f065e7',
  message: 'ew honor suffocation',
  estimatedPickupFromDate: dayjs('2024-03-21T09:22'),
  estimatedPickupUntilDate: dayjs('2024-03-21T09:00'),
  estimatedDropoffFromDate: dayjs('2024-03-22T01:39'),
  estimatedDropoffUntilDate: dayjs('2024-03-21T09:43'),
  state: 'WITHDRAWN',
  price: 28380.8,
  carrierId: 'ba8ae83b-bde4-4eb7-ad0c-a6e3f246bc37',
  truckId: '50d81e3d-287e-474d-ba86-ce400e37d6d6',
};

export const sampleWithNewData: NewOffer = {
  estimatedPickupFromDate: dayjs('2024-03-22T04:46'),
  estimatedPickupUntilDate: dayjs('2024-03-21T08:41'),
  estimatedDropoffFromDate: dayjs('2024-03-22T02:09'),
  estimatedDropoffUntilDate: dayjs('2024-03-22T01:31'),
  state: 'PENDING',
  price: 16330.89,
  carrierId: 'f3c15f0f-d3fd-48f2-bf72-b26511761bb4',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
