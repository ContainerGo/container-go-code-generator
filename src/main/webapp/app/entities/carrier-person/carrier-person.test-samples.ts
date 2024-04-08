import { ICarrierPerson, NewCarrierPerson } from './carrier-person.model';

export const sampleWithRequiredData: ICarrierPerson = {
  id: '662bbc66-ead9-42e9-8aba-2582231be512',
  name: 'yesterday needle oppose',
  phone: '0243 4516 6038',
};

export const sampleWithPartialData: ICarrierPerson = {
  id: '08065690-45a1-4dea-8aea-1199a73d7bc6',
  name: 'loftily act',
  phone: '025 0643 3877',
  address: 'gopher whoa',
};

export const sampleWithFullData: ICarrierPerson = {
  id: 'b9e40e08-c0b0-4fb6-9ddb-bac4716c54d5',
  name: 'weight',
  phone: '0205 9943 5193',
  email: 'KimThu51@gmail.com',
  address: 'blue voluntarily immediately',
};

export const sampleWithNewData: NewCarrierPerson = {
  name: 'ouch unlike athwart',
  phone: '0262 2546 3302',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
