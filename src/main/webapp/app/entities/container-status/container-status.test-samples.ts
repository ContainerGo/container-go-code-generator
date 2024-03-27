import { IContainerStatus, NewContainerStatus } from './container-status.model';

export const sampleWithRequiredData: IContainerStatus = {
  id: 9818,
  code: 'however pesky',
  name: 'unless',
};

export const sampleWithPartialData: IContainerStatus = {
  id: 25020,
  code: 'knowingly blah',
  name: 'when',
};

export const sampleWithFullData: IContainerStatus = {
  id: 15313,
  code: 'gah female',
  name: 'aw plus gee',
  description: 'blah',
};

export const sampleWithNewData: NewContainerStatus = {
  code: 'irritably though fearless',
  name: 'instead troubled weak',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
