import { IContainerStatus, NewContainerStatus } from './container-status.model';

export const sampleWithRequiredData: IContainerStatus = {
  id: '4a793438-4a9e-46ae-8901-14467bdc6818',
  code: 'pale ballpark banter',
  name: 'gruesome',
};

export const sampleWithPartialData: IContainerStatus = {
  id: 'c94e9c35-b281-4cc7-8c7b-454a5595ab44',
  code: 'absent metabolize',
  name: 'ugh',
};

export const sampleWithFullData: IContainerStatus = {
  id: '4d412496-663c-477d-ad8a-7afbf1176846',
  code: 'and mallet',
  name: 'drat now',
  description: 'excepting but aid',
};

export const sampleWithNewData: NewContainerStatus = {
  code: 'gah um enthusiastically',
  name: 'now fooey',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
