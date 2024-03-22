import { IContainerType, NewContainerType } from './container-type.model';

export const sampleWithRequiredData: IContainerType = {
  id: 2786,
  code: 'fast box',
  name: 'spotted dizzy',
};

export const sampleWithPartialData: IContainerType = {
  id: 24642,
  code: 'vapid very handmaiden',
  name: 'deliberately when',
};

export const sampleWithFullData: IContainerType = {
  id: 23969,
  code: 'sideburns since',
  name: 'birthday septicaemia',
  description: 'carefree aw consult',
};

export const sampleWithNewData: NewContainerType = {
  code: 'infinite',
  name: 'plus easy-going cluster',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
