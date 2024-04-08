import { IDistrict, NewDistrict } from './district.model';

export const sampleWithRequiredData: IDistrict = {
  id: '6fa387c6-d0be-4124-a79c-0313f8fe38a4',
  code: 'troubled',
  name: 'assent awesome',
};

export const sampleWithPartialData: IDistrict = {
  id: '39f9ab31-4098-430c-a886-48d560801bf9',
  code: 'athwart incidentally without',
  name: 'land',
};

export const sampleWithFullData: IDistrict = {
  id: '4e1b87fb-a47e-4505-925b-8c3d385919aa',
  code: 'accidentally distress interpose',
  name: 'doctorate woot',
  description: 'broadly deliberately unknown',
};

export const sampleWithNewData: NewDistrict = {
  code: 'testy now',
  name: 'slushy sever as',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
