import { ICarrierPerson, NewCarrierPerson } from './carrier-person.model';

export const sampleWithRequiredData: ICarrierPerson = {
  id: 13153,
  name: 'while gee',
  phone: '0261 3511 2079',
};

export const sampleWithPartialData: ICarrierPerson = {
  id: 4864,
  name: 'huzzah',
  phone: '0260 4352 5269',
  email: 'DanhSon.Duong@gmail.com',
  address: 'down',
};

export const sampleWithFullData: ICarrierPerson = {
  id: 28631,
  name: 'gastronomy uh-huh carelessly',
  phone: '0256 4284 6741',
  email: 'KhacMinh39@yahoo.com',
  address: 'act beside',
};

export const sampleWithNewData: NewCarrierPerson = {
  name: 'pfft phooey doctor',
  phone: '028 0570 7096',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
