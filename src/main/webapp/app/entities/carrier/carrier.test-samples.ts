import dayjs from 'dayjs/esm';

import { ICarrier, NewCarrier } from './carrier.model';

export const sampleWithRequiredData: ICarrier = {
  id: 7274,
  code: 'mostly',
  name: 'normalise jumbo untried',
  address: 'whenever',
};

export const sampleWithPartialData: ICarrier = {
  id: 29725,
  code: 'overconfidently inasmuch',
  name: 'wrap lest',
  address: 'anti honour inasmuch',
  bankAccount: 'inasmuch ghostwrite',
  bankName: 'perplex paddle nurture',
  accountName: 'Credit Card Account',
  vehicles: 8218,
  shipmentsLeftForDay: 9250,
};

export const sampleWithFullData: ICarrier = {
  id: 16285,
  code: 'apud',
  name: 'daintily',
  address: 'free alleviate times',
  taxCode: 'next before now',
  bankAccount: 'notwithstanding voluntarily',
  bankName: 'eek',
  accountName: 'Auto Loan Account',
  branchName: 'woot accumulate why',
  companySize: 2073,
  isApproved: true,
  vehicles: 29630,
  shipmentsLeftForDay: 6059,
  verifiedSince: dayjs('2024-03-12T22:34'),
};

export const sampleWithNewData: NewCarrier = {
  code: 'motorcar following maintainer',
  name: 'which',
  address: 'post um gah',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
