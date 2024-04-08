import { IWard, NewWard } from './ward.model';

export const sampleWithRequiredData: IWard = {
  id: 'f85f26e1-c200-4019-877d-a69eb5f7a05b',
  code: 'since',
  name: 'belated er',
};

export const sampleWithPartialData: IWard = {
  id: 'a8a3a95a-d339-4d6b-853b-f4b9993132ef',
  code: 'wassail',
  name: 'pint yet',
};

export const sampleWithFullData: IWard = {
  id: 'fb958c33-6370-4a9e-94ea-a4439c8f8843',
  code: 'telegraph yum',
  name: 'infinite',
  description: 'permit in',
};

export const sampleWithNewData: NewWard = {
  code: 'yahoo brr',
  name: 'aha gossip following',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
