import { ITruck, NewTruck } from './truck.model';

export const sampleWithRequiredData: ITruck = {
  id: '36ebe332-a438-4019-a03d-51be7c8119d1',
  code: 'until tantalize manacle',
  name: 'molecule',
  status: 'IN_TRANSIT',
  numberPlate: 'likewise uh-huh',
};

export const sampleWithPartialData: ITruck = {
  id: 'ebac856f-71e5-4cb6-9588-89dadfd769d2',
  code: 'snip',
  name: 'yet',
  model: 'query',
  capacity: 18972.8,
  status: 'IN_TRANSIT',
  numberPlate: 'payoff',
  lat: 9839.11,
};

export const sampleWithFullData: ITruck = {
  id: '47cd0186-9927-4069-929d-ce8bf2e887f8',
  code: 'crave comment fuzzy',
  name: 'fondly regularize',
  model: 'psst atop woot',
  manufacturer: 'cascade',
  year: 31355,
  capacity: 18523.3,
  status: 'IN_TRANSIT',
  mileage: 4999.98,
  numberPlate: 'like',
  lat: 14096.35,
  lng: 12774.3,
};

export const sampleWithNewData: NewTruck = {
  code: 'droopy',
  name: 'beatboxer paralyse regarding',
  status: 'IN_TRANSIT',
  numberPlate: 'woozy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
