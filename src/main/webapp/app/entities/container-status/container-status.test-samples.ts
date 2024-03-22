import { IContainerStatus, NewContainerStatus } from './container-status.model';

export const sampleWithRequiredData: IContainerStatus = {
  id: 23809,
  code: 'emancipate tire though',
  name: 'kind upbeat',
};

export const sampleWithPartialData: IContainerStatus = {
  id: 5020,
  code: 'next lazily abjure',
  name: 'whenever indeed valiantly',
};

export const sampleWithFullData: IContainerStatus = {
  id: 10903,
  code: 'but because',
  name: 'woot',
  description: 'eek',
};

export const sampleWithNewData: NewContainerStatus = {
  code: 'campaign candy',
  name: 'into drat',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
