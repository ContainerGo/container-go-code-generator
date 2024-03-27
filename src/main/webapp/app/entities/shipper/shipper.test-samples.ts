import { IShipper, NewShipper } from './shipper.model';

export const sampleWithRequiredData: IShipper = {
  id: 17686,
  code: 'astride round nutritious',
  name: 'forebear tomorrow',
  address: 'generously',
  paymentType: 'amidst',
};

export const sampleWithPartialData: IShipper = {
  id: 28177,
  code: 'gosh',
  name: 'tutor natural gee',
  address: 'furthermore hm',
  taxCode: 'yahoo yum',
  paymentType: 'ah',
  isApproved: false,
  isProfileComplete: false,
};

export const sampleWithFullData: IShipper = {
  id: 5498,
  code: 'ack tremendously nifty',
  name: 'litmus',
  address: 'mysteriously brocolli',
  taxCode: 'decimal unabashedly staple',
  companySize: 345,
  paymentType: 'back-up',
  isApproved: true,
  isBillingInformationComplete: false,
  isProfileComplete: false,
};

export const sampleWithNewData: NewShipper = {
  code: 'while regenerate upbeat',
  name: 'beloved gosh disallow',
  address: 'guava furthermore times',
  paymentType: 'ouch schema',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
