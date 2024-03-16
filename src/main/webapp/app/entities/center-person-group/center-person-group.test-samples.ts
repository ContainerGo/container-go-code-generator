import { ICenterPersonGroup, NewCenterPersonGroup } from './center-person-group.model';

export const sampleWithRequiredData: ICenterPersonGroup = {
  id: 2617,
  name: 'even desegregate',
};

export const sampleWithPartialData: ICenterPersonGroup = {
  id: 3722,
  name: 'wind',
  description: 'broadly harmful',
};

export const sampleWithFullData: ICenterPersonGroup = {
  id: 18163,
  name: 'prohibit apropos innocently',
  description: 'flintlock an',
};

export const sampleWithNewData: NewCenterPersonGroup = {
  name: 'er',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
