import { IDistrict, NewDistrict } from './district.model';

export const sampleWithRequiredData: IDistrict = {
  id: 28452,
  code: 'nor delightfully regularly',
  name: 'blaspheme',
};

export const sampleWithPartialData: IDistrict = {
  id: 906,
  code: 'hideous',
  name: 'yum unblock',
};

export const sampleWithFullData: IDistrict = {
  id: 18383,
  code: 'plus trouble',
  name: 'trickle geez',
  description: 'gah surrender',
};

export const sampleWithNewData: NewDistrict = {
  code: 'anenst manager apropos',
  name: 'athwart toady inasmuch',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
