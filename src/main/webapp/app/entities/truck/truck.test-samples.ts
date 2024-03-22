import { ITruck, NewTruck } from './truck.model';

export const sampleWithRequiredData: ITruck = {
  id: 31504,
  code: 'soil esteemed eek',
  name: 'what',
};

export const sampleWithPartialData: ITruck = {
  id: 23860,
  code: 'unnecessarily',
  name: 'aw',
};

export const sampleWithFullData: ITruck = {
  id: 20025,
  code: 'uh-huh hardening aardvark',
  name: 'swig hmph',
};

export const sampleWithNewData: NewTruck = {
  code: 'by',
  name: 'muffled summit clash',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
