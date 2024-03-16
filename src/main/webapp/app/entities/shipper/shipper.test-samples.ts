import { IShipper, NewShipper } from './shipper.model';

export const sampleWithRequiredData: IShipper = {
  id: 17402,
  code: 'enraged',
  name: 'ah',
  address: 'across',
};

export const sampleWithPartialData: IShipper = {
  id: 6656,
  code: 'now',
  name: 'an likable at',
  address: 'where growing meh',
  taxCode: 'sociable failing',
  bankAccount: 'since',
  bankName: 'even blah',
  accountName: 'Savings Account',
};

export const sampleWithFullData: IShipper = {
  id: 20698,
  code: 'until lambast jeans',
  name: 'methane till whoa',
  address: 'amid prioritize of',
  taxCode: 'round',
  bankAccount: 'agonizing',
  bankName: 'playfully',
  accountName: 'Personal Loan Account',
  branchName: 'disprove because limply',
};

export const sampleWithNewData: NewShipper = {
  code: 'whoa mmm wavy',
  name: 'gee',
  address: 'brr freeze clean',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
