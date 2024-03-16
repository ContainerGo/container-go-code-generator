import { ICarrierPerson, NewCarrierPerson } from './carrier-person.model';

export const sampleWithRequiredData: ICarrierPerson = {
  id: 22182,
  name: 'though',
  phone: '0220 1937 7369',
};

export const sampleWithPartialData: ICarrierPerson = {
  id: 20669,
  name: 'frightfully barring whose',
  phone: '0249 2103 8643',
};

export const sampleWithFullData: ICarrierPerson = {
  id: 19640,
  name: 'rudely constant',
  phone: '023 5801 3499',
  email: '7kanQue.Phung2@hotmail.com',
  address: 'er inwardly',
};

export const sampleWithNewData: NewCarrierPerson = {
  name: 'unless crisp',
  phone: '0278 1325 9585',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
