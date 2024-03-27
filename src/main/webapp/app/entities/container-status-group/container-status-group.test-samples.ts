import { IContainerStatusGroup, NewContainerStatusGroup } from './container-status-group.model';

export const sampleWithRequiredData: IContainerStatusGroup = {
  id: 12926,
  code: 'hence puny solid',
  name: 'oh',
};

export const sampleWithPartialData: IContainerStatusGroup = {
  id: 19001,
  code: 'randomize failing',
  name: 'namecheck',
};

export const sampleWithFullData: IContainerStatusGroup = {
  id: 10449,
  code: 'since',
  name: 'until even',
  description: 'phew',
};

export const sampleWithNewData: NewContainerStatusGroup = {
  code: 'supposing',
  name: 'safely mmm',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
