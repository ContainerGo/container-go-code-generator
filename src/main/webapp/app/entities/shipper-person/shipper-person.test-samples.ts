import { IShipperPerson, NewShipperPerson } from './shipper-person.model';

export const sampleWithRequiredData: IShipperPerson = {
  id: 3113,
  name: 'glare into consider',
  phone: '027 5534 7307',
};

export const sampleWithPartialData: IShipperPerson = {
  id: 10928,
  name: 'beyond equal yippee',
  phone: '021 0291 2418',
  address: 'afore calculus ha',
};

export const sampleWithFullData: IShipperPerson = {
  id: 13303,
  name: 'quench aw coleslaw',
  phone: '0251 1111 8393',
  email: 'ThuVan15@hotmail.com',
  address: 'how fast hospitalisation',
};

export const sampleWithNewData: NewShipperPerson = {
  name: 'ruins what clogs',
  phone: '0250 5474 1242',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
