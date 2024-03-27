import { ITruckType, NewTruckType } from './truck-type.model';

export const sampleWithRequiredData: ITruckType = {
  id: 29002,
  code: 'apropos unless justly',
  name: 'both',
};

export const sampleWithPartialData: ITruckType = {
  id: 9212,
  code: 'fingerling about',
  name: 'dangerous noxious',
  height: 19458,
  type: 'underneath ha',
  width: 20766,
};

export const sampleWithFullData: ITruckType = {
  id: 8091,
  code: 'ripe technician',
  name: 'fortunately elementary',
  category: 'whose stranger',
  height: 30930,
  length: 6924,
  maxSpeed: 4214.89,
  type: 'out ew whoa',
  weight: 20189,
  width: 13162,
};

export const sampleWithNewData: NewTruckType = {
  code: 'yet amuse',
  name: 'geez uh-huh',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
