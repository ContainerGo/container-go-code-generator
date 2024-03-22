import { IContainerStatusGroup, NewContainerStatusGroup } from './container-status-group.model';

export const sampleWithRequiredData: IContainerStatusGroup = {
  id: 5442,
  code: 'unto plump sneakers',
  name: 'hence',
};

export const sampleWithPartialData: IContainerStatusGroup = {
  id: 29491,
  code: 'worth damaged pish',
  name: 'ancient pervade prevention',
};

export const sampleWithFullData: IContainerStatusGroup = {
  id: 7568,
  code: 'jealousy',
  name: 'whenever',
  description: 'pessimistic',
};

export const sampleWithNewData: NewContainerStatusGroup = {
  code: 'when rake',
  name: 'inasmuch excepting too',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
