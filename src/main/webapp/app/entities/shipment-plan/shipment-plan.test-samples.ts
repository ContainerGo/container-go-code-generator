import dayjs from 'dayjs/esm';

import { IShipmentPlan, NewShipmentPlan } from './shipment-plan.model';

export const sampleWithRequiredData: IShipmentPlan = {
  id: '13bd6a93-cc96-4e21-96f7-f60eeeaeac62',
  estimatedPickupFromDate: dayjs('2024-04-07T11:03'),
  estimatedPickupUntilDate: dayjs('2024-04-07T13:02'),
  estimatedDropoffFromDate: dayjs('2024-04-07T07:32'),
  estimatedDropoffUntilDate: dayjs('2024-04-07T22:20'),
  driverId: 'f720a650-bf0a-465e-8518-ee00c6d14f85',
  truckId: 'e63df6f6-1617-4894-a52c-e8c17f94f25f',
};

export const sampleWithPartialData: IShipmentPlan = {
  id: 'ee51bc17-f590-4f70-8e88-b509eec04653',
  estimatedPickupFromDate: dayjs('2024-04-07T14:20'),
  estimatedPickupUntilDate: dayjs('2024-04-07T23:19'),
  estimatedDropoffFromDate: dayjs('2024-04-07T09:52'),
  estimatedDropoffUntilDate: dayjs('2024-04-07T14:11'),
  driverId: 'a8a8073d-0004-43a5-8081-f25fb2d780b7',
  truckId: 'acf1d5f9-c750-4326-99ad-b14078e061f9',
};

export const sampleWithFullData: IShipmentPlan = {
  id: 'ed96a139-e905-4442-bed5-b5166276cc5c',
  estimatedPickupFromDate: dayjs('2024-04-07T09:15'),
  estimatedPickupUntilDate: dayjs('2024-04-07T21:42'),
  estimatedDropoffFromDate: dayjs('2024-04-07T05:46'),
  estimatedDropoffUntilDate: dayjs('2024-04-07T09:49'),
  driverId: 'cdd35823-88f9-43a2-90f8-f8b1c0bf960b',
  truckId: '1ba636ce-0964-43be-a0a9-46944369203a',
};

export const sampleWithNewData: NewShipmentPlan = {
  estimatedPickupFromDate: dayjs('2024-04-07T22:48'),
  estimatedPickupUntilDate: dayjs('2024-04-07T11:22'),
  estimatedDropoffFromDate: dayjs('2024-04-07T07:32'),
  estimatedDropoffUntilDate: dayjs('2024-04-07T09:39'),
  driverId: 'cc1b5289-5d3c-461d-bbfb-9f7ecb169501',
  truckId: 'ed3a2051-15ba-4e5a-8bf2-0395f91ef591',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
