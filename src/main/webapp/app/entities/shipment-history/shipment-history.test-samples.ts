import dayjs from 'dayjs/esm';

import { IShipmentHistory, NewShipmentHistory } from './shipment-history.model';

export const sampleWithRequiredData: IShipmentHistory = {
  id: '536ed583-f6a9-42b3-808a-3ea21d719446',
  event: 'checkbook hm blah',
  timestamp: dayjs('2024-03-26T15:33'),
  executedBy: 'rest',
};

export const sampleWithPartialData: IShipmentHistory = {
  id: '3bd45ce8-2868-4163-80eb-c464629495c0',
  event: 'savour',
  timestamp: dayjs('2024-03-26T16:15'),
  executedBy: 'failing',
};

export const sampleWithFullData: IShipmentHistory = {
  id: '29cf5062-cd6a-4a06-8c9e-729776dcab84',
  event: 'sunglasses grandiose faithful',
  timestamp: dayjs('2024-03-27T01:09'),
  executedBy: 'thing duh entrust',
  location: 'blow fame',
  lat: 31630.94,
  lng: 7614.14,
};

export const sampleWithNewData: NewShipmentHistory = {
  event: 'lined offensively',
  timestamp: dayjs('2024-03-26T14:45'),
  executedBy: 'briefly even',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
