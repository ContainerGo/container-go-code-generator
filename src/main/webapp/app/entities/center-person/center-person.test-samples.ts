import { ICenterPerson, NewCenterPerson } from './center-person.model';

export const sampleWithRequiredData: ICenterPerson = {
  id: 9355,
  name: 'numeracy',
  phone: '0217 7649 7821',
};

export const sampleWithPartialData: ICenterPerson = {
  id: 21892,
  name: 'until forenenst equally',
  phone: '0227 6203 5939',
};

export const sampleWithFullData: ICenterPerson = {
  id: 21593,
  name: 'swiftly meh',
  phone: '0265 5022 1542',
  email: 'MinhThu14@yahoo.com',
  address: 'key drat',
};

export const sampleWithNewData: NewCenterPerson = {
  name: 'blindly sulks',
  phone: '0252 6909 4728',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
