import { IWard, NewWard } from './ward.model';

export const sampleWithRequiredData: IWard = {
  id: 31938,
  code: 'ack meanwhile',
  name: 'gripping cameo',
};

export const sampleWithPartialData: IWard = {
  id: 20728,
  code: 'bah twine pace',
  name: 'purl resolve',
  description: 'nice why',
};

export const sampleWithFullData: IWard = {
  id: 5040,
  code: 'wassail',
  name: 'pint yet',
  description: 'deserve treat ferociously',
};

export const sampleWithNewData: NewWard = {
  code: 'cultivate revitalization',
  name: 'but overcooked enthusiastically',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
