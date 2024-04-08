import { ITruckType, NewTruckType } from './truck-type.model';

export const sampleWithRequiredData: ITruckType = {
  id: 'eee236ed-2ffc-451c-8be7-cf4564ee78ef',
  code: 'behind guideline snail',
  name: 'optimistic',
};

export const sampleWithPartialData: ITruckType = {
  id: '999663d0-8eee-421e-a9ba-378dbbe8bf22',
  code: 'elaborate that',
  name: 'thoughtful hype uselessly',
  category: 'since crop of',
  height: 13162,
  length: 11685,
  maxSpeed: 25853.86,
};

export const sampleWithFullData: ITruckType = {
  id: '4121ff48-0766-4093-a018-713a08ba5de7',
  code: 'ugh nucleotide next',
  name: 'psst excitable tenderly',
  category: 'quizzically anenst inch',
  height: 19315,
  length: 24184,
  maxSpeed: 2807.51,
  type: 'above pillow',
  weight: 8274,
  width: 29718,
};

export const sampleWithNewData: NewTruckType = {
  code: 'vocalize',
  name: 'apropos ultimate next',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
