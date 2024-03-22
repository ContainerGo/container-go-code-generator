import { IWard, NewWard } from './ward.model';

export const sampleWithRequiredData: IWard = {
  id: 2861,
  code: 'abnormally times great-grandmother',
  name: 'perfume lightly',
};

export const sampleWithPartialData: IWard = {
  id: 11399,
  code: 'gadzooks',
  name: 'judgementally',
  description: 'earn dolman',
};

export const sampleWithFullData: IWard = {
  id: 25349,
  code: 'with',
  name: 'tumbler unfit',
  description: 'yum',
};

export const sampleWithNewData: NewWard = {
  code: 'however now',
  name: 'blah',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
