import { IProvice, NewProvice } from './provice.model';

export const sampleWithRequiredData: IProvice = {
  id: 15272,
  code: 'diffract boo pitcher',
  name: 'afore who pirouette',
};

export const sampleWithPartialData: IProvice = {
  id: 27588,
  code: 'duck punctually meet',
  name: 'doctorate iconify brightly',
  description: 'strawberry delightfully teeter',
};

export const sampleWithFullData: IProvice = {
  id: 29129,
  code: 'unto',
  name: 'pop through',
  description: 'honestly er yahoo',
};

export const sampleWithNewData: NewProvice = {
  code: 'wetly',
  name: 'where trusting',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
