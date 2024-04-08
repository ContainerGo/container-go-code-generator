import { IProvice, NewProvice } from './provice.model';

export const sampleWithRequiredData: IProvice = {
  id: '7d421dc7-33c4-41cc-8152-389aaee2dadc',
  code: 'yippee despite loyally',
  name: 'gah grouchy until',
};

export const sampleWithPartialData: IProvice = {
  id: '495a6266-68b2-4d51-ac32-a7e6dda22c72',
  code: 'disguised unto convulse',
  name: 'through election punctually',
};

export const sampleWithFullData: IProvice = {
  id: 'efe84315-262c-4cf4-bf82-c1565c9b586a',
  code: 'once provided through',
  name: 'opposite modulo era',
  description: 'across instead',
};

export const sampleWithNewData: NewProvice = {
  code: 'along anenst',
  name: 'sans font',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
