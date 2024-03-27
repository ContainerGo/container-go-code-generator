import dayjs from 'dayjs/esm';

import { IShipmentHistory, NewShipmentHistory } from './shipment-history.model';

export const sampleWithRequiredData: IShipmentHistory = {
  id: 10531,
  event: 'vest',
  timestamp: dayjs('2024-03-26T18:13'),
  executedBy: 'though now',
};

export const sampleWithPartialData: IShipmentHistory = {
  id: 10147,
  event: 'sans furthermore',
  timestamp: dayjs('2024-03-26T13:39'),
  executedBy: 'blah wherever',
  location: 'gosh',
  lng: 8213.69,
};

export const sampleWithFullData: IShipmentHistory = {
  id: 10842,
  event: 'buzz drawing than',
  timestamp: dayjs('2024-03-26T20:26'),
  executedBy: 'brr rigidly of',
  location: 'throughout midst woot',
  lat: 27658.18,
  lng: 24955.56,
};

export const sampleWithNewData: NewShipmentHistory = {
  event: 'flounce soggy internal',
  timestamp: dayjs('2024-03-26T13:54'),
  executedBy: 'tab caption prosperity',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
