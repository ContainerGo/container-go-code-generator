import { IContainerType, NewContainerType } from './container-type.model';

export const sampleWithRequiredData: IContainerType = {
  id: 10448,
  code: 'whenever where',
  name: 'used um defile',
};

export const sampleWithPartialData: IContainerType = {
  id: 12015,
  code: 'where even tantalize',
  name: 'anti football',
  description: 'uh-huh support aw',
};

export const sampleWithFullData: IContainerType = {
  id: 25383,
  code: 'heed kaleidoscopic',
  name: 'aw satirize apud',
  description: 'leverage',
};

export const sampleWithNewData: NewContainerType = {
  code: 'oh root vacantly',
  name: 'before',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
