import { ICenterPerson, NewCenterPerson } from './center-person.model';

export const sampleWithRequiredData: ICenterPerson = {
  id: '441a50c0-9f2c-4c96-bece-419eae8b6492',
  name: 'reignite shrilly mid',
  phone: '0239 6337 1713',
};

export const sampleWithPartialData: ICenterPerson = {
  id: 'f84c57ca-9913-4338-b747-607d227ddeef',
  name: 'drat nor',
  phone: '022 0087 0875',
};

export const sampleWithFullData: ICenterPerson = {
  id: '4ae1e6b3-d7d0-4f0d-81fa-cec7c53bd4d4',
  name: 'repulsive speedily',
  phone: '0271 9993 4704',
  email: 'ThanhMan_Duong91@hotmail.com',
  address: 'silently ketchup though',
};

export const sampleWithNewData: NewCenterPerson = {
  name: 'thoroughly since',
  phone: '029 6080 5405',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
