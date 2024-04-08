import dayjs from 'dayjs/esm';

import { ICarrier, NewCarrier } from './carrier.model';

export const sampleWithRequiredData: ICarrier = {
  id: 'dc33eced-0488-4934-8e6c-6c3de2c5ec67',
  code: 'canonise',
  name: 'extraneous oddball',
  address: 'qualified considerate next',
};

export const sampleWithPartialData: ICarrier = {
  id: 'c14bb093-f86f-402b-98c1-ef3536d3d57c',
  code: 'pitch what trait',
  name: 'daylight next',
  address: 'bashfully incidentally or',
  bankAccount: 'innocently admin',
  companySize: 26963,
  isApproved: true,
  vehicles: 23584,
};

export const sampleWithFullData: ICarrier = {
  id: '28348ce2-00d2-4df8-814c-ba06fd0b13e3',
  code: 'concerning how',
  name: 'boohoo',
  address: 'needily gosh as',
  taxCode: 'absentmindedly afore',
  bankAccount: 'desolate carefully',
  bankName: 'grubby',
  accountName: 'Credit Card Account',
  branchName: 'coolly quickly',
  companySize: 3685,
  isApproved: true,
  vehicles: 15786,
  shipmentsLeftForDay: 30604,
  verifiedSince: dayjs('2024-03-12T09:08'),
};

export const sampleWithNewData: NewCarrier = {
  code: 'how aside',
  name: 'until',
  address: 'infantile geez quarrelsomely',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
