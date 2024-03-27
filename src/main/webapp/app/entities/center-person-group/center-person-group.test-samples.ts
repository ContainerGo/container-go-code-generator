import { ICenterPersonGroup, NewCenterPersonGroup } from './center-person-group.model';

export const sampleWithRequiredData: ICenterPersonGroup = {
  id: 31167,
  name: 'if repeatedly',
};

export const sampleWithPartialData: ICenterPersonGroup = {
  id: 3758,
  name: 'cent huzzah the',
};

export const sampleWithFullData: ICenterPersonGroup = {
  id: 29137,
  name: 'scarily category meh',
  description: 'uh-huh swiftly',
};

export const sampleWithNewData: NewCenterPersonGroup = {
  name: 'gadzooks',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
