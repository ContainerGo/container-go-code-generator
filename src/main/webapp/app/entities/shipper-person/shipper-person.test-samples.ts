import { IShipperPerson, NewShipperPerson } from './shipper-person.model';

export const sampleWithRequiredData: IShipperPerson = {
  id: 21128,
  name: 'diligently',
  phone: '022 9857 9642',
};

export const sampleWithPartialData: IShipperPerson = {
  id: 7686,
  name: 'unabashedly who pamper',
  phone: '027 2550 7262',
  address: 'crease honest',
};

export const sampleWithFullData: IShipperPerson = {
  id: 30989,
  name: 'outlying quarrelsomely nor',
  phone: '0219 9736 7077',
  email: 'PhuHai_Phung@hotmail.com',
  address: 'perception big',
};

export const sampleWithNewData: NewShipperPerson = {
  name: 'color uh-huh',
  phone: '0290 9006 2550',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
