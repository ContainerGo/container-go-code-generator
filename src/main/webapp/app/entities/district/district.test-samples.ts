import { IDistrict, NewDistrict } from './district.model';

export const sampleWithRequiredData: IDistrict = {
  id: 12858,
  code: 'pish meaningfully warmly',
  name: 'readiness um',
};

export const sampleWithPartialData: IDistrict = {
  id: 18255,
  code: 'restart via emotional',
  name: 'rupture',
};

export const sampleWithFullData: IDistrict = {
  id: 18244,
  code: 'amnesty placode',
  name: 'athwart incidentally without',
  description: 'land',
};

export const sampleWithNewData: NewDistrict = {
  code: 'pace',
  name: 'yowza athwart',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
