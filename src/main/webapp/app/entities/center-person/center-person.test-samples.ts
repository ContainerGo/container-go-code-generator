import { ICenterPerson, NewCenterPerson } from './center-person.model';

export const sampleWithRequiredData: ICenterPerson = {
  id: 8780,
  name: 'or per ambush',
  phone: '0296 4802 6535',
};

export const sampleWithPartialData: ICenterPerson = {
  id: 14852,
  name: 'quintessential',
  phone: '028 0995 4857',
  email: 'LongGiang_Ha@hotmail.com',
  address: 'safe huzzah',
};

export const sampleWithFullData: ICenterPerson = {
  id: 22094,
  name: 'colorlessness',
  phone: '0251 8527 8447',
  email: 'MyLoi_Phung@gmail.com',
  address: 'ouch surprisingly',
};

export const sampleWithNewData: NewCenterPerson = {
  name: 'weather bloom',
  phone: '0289 2995 6466',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
