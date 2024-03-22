import { ITruckType, NewTruckType } from './truck-type.model';

export const sampleWithRequiredData: ITruckType = {
  id: 1876,
  code: 'defenseless ew rusty',
  name: 'blast',
};

export const sampleWithPartialData: ITruckType = {
  id: 26146,
  code: 'depreciate outlying evolution',
  name: 'vital psst separately',
  category: 'uselessly successfully',
  height: 30394,
  type: 'oxidise worriedly since',
  weight: 25424,
  width: 8304,
};

export const sampleWithFullData: ITruckType = {
  id: 19081,
  code: 'likewise boo bargain',
  name: 'following',
  category: 'phooey',
  height: 21266,
  length: 21650,
  maxSpeed: 7641.39,
  type: 'along',
  weight: 4984,
  width: 9096,
};

export const sampleWithNewData: NewTruckType = {
  code: 'windy cadet yuck',
  name: 'worth',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
