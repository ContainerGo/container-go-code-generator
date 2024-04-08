import { IContainerType, NewContainerType } from './container-type.model';

export const sampleWithRequiredData: IContainerType = {
  id: '59daf32c-cfed-4c1f-bcec-f39eaed6f4d1',
  code: 'majestically',
  name: 'perfectly solemnly',
};

export const sampleWithPartialData: IContainerType = {
  id: 'b62109e6-90b4-4f82-ad2a-83b5dca708dd',
  code: 'ah',
  name: 'miserably',
  description: 'till perfumed doubter',
};

export const sampleWithFullData: IContainerType = {
  id: 'dbea1c22-ea7f-4303-ae7f-0b8bb626c202',
  code: 'shabby texture',
  name: 'acre',
  description: 'bassoon yowza',
};

export const sampleWithNewData: NewContainerType = {
  code: 'gah poorly phooey',
  name: 'ick bulk',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
