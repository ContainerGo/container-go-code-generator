import { ICarrier, NewCarrier } from './carrier.model';

export const sampleWithRequiredData: ICarrier = {
  id: 4344,
  code: 'part',
  name: 'grasshopper',
  address: 'consequently simple fooey',
};

export const sampleWithPartialData: ICarrier = {
  id: 19799,
  code: 'abominate aw petal',
  name: 'because',
  address: 'except jovially',
  bankAccount: 'yum triumphantly centre',
  branchName: 'superb unless',
  companySize: 5303,
  isApproved: false,
};

export const sampleWithFullData: ICarrier = {
  id: 7005,
  code: 'paramecium bulb',
  name: 'colonial daintily um',
  address: 'alleviate times',
  taxCode: 'next before now',
  bankAccount: 'notwithstanding voluntarily',
  bankName: 'eek',
  accountName: 'Auto Loan Account',
  branchName: 'woot accumulate why',
  companySize: 2073,
  isApproved: true,
};

export const sampleWithNewData: NewCarrier = {
  code: 'considering relent yoke',
  name: 'because',
  address: 'obediently regularly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
