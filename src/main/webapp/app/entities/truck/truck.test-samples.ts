import { ITruck, NewTruck } from './truck.model';

export const sampleWithRequiredData: ITruck = {
  id: 7698,
  code: 'quirkily accommodation',
  name: 'sans',
  status: 'UNDER_MAINTENANCE',
  numberPlate: 'with',
};

export const sampleWithPartialData: ITruck = {
  id: 23592,
  code: 'toil',
  name: 'prioritize geez',
  manufacturer: 'downgrade',
  capacity: 28415.41,
  status: 'IN_TRANSIT',
  numberPlate: 'gadzooks mock but',
};

export const sampleWithFullData: ITruck = {
  id: 24894,
  code: 'thin than eventuate',
  name: 'boohoo cautiously',
  model: 'absent',
  manufacturer: 'forceful',
  year: 8857,
  capacity: 14715.48,
  status: 'OUT_OF_SERVICE',
  mileage: 27659.36,
  numberPlate: 'given',
  lat: 626.97,
  lng: 13064.82,
};

export const sampleWithNewData: NewTruck = {
  code: 'scrawny um',
  name: 'guitarist geez',
  status: 'OUT_OF_SERVICE',
  numberPlate: 'fuzzy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
