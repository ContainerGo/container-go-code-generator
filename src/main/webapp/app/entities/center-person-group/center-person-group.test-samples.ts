import { ICenterPersonGroup, NewCenterPersonGroup } from './center-person-group.model';

export const sampleWithRequiredData: ICenterPersonGroup = {
  id: '5afb22d7-3913-4ec0-8d5c-f225cf5359d2',
  name: 'range lard incidentally',
};

export const sampleWithPartialData: ICenterPersonGroup = {
  id: '74e3740d-1da2-4adc-881c-3a9ce9c49545',
  name: 'overconfidently total',
};

export const sampleWithFullData: ICenterPersonGroup = {
  id: 'ffa68f0a-3ff5-4f14-9981-3edb9cb6dc3e',
  name: 'experienced crochet',
};

export const sampleWithNewData: NewCenterPersonGroup = {
  name: 'wobbly below poorly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
