import { ITruck, NewTruck } from './truck.model';

export const sampleWithRequiredData: ITruck = {
  id: 3952,
  code: 'esteemed eek payment',
  name: 'dislodge for kissingly',
  status: 'AVAILABLE',
  numberPlate: 'daily black-and-white behind',
};

export const sampleWithPartialData: ITruck = {
  id: 369,
  code: 'monthly',
  name: 'down standardization regularly',
  status: 'AVAILABLE',
  numberPlate: 'absent zowie yahoo',
};

export const sampleWithFullData: ITruck = {
  id: 28806,
  code: 'accidentally epitomise',
  name: 'wherever',
  model: 'quietly keen gosh',
  manufacturer: 'cash synod frantically',
  year: 18212,
  capacity: 29402.98,
  status: 'OUT_OF_SERVICE',
  mileage: 10786.15,
  numberPlate: 'smother',
};

export const sampleWithNewData: NewTruck = {
  code: 'evolve intensely blather',
  name: 'with low potentially',
  status: 'AVAILABLE',
  numberPlate: 'hmph',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
