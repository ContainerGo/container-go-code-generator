import { IProvice, NewProvice } from './provice.model';

export const sampleWithRequiredData: IProvice = {
  id: 9986,
  code: 'precedent ugly vaguely',
  name: 'fracture',
};

export const sampleWithPartialData: IProvice = {
  id: 17976,
  code: 'forsaken',
  name: 'if',
  description: 'yippee gadzooks',
};

export const sampleWithFullData: IProvice = {
  id: 28579,
  code: 'meh',
  name: 'than darling',
  description: 'ascent conventional sound',
};

export const sampleWithNewData: NewProvice = {
  code: 'assured wooden',
  name: 'gadzooks ah jumbo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
