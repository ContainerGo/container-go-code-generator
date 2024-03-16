import { ICarrier, NewCarrier } from './carrier.model';

export const sampleWithRequiredData: ICarrier = {
  id: 8116,
  code: 'why magnificent yuck',
  name: 'liken',
  address: 'but faithful foolishly',
};

export const sampleWithPartialData: ICarrier = {
  id: 26947,
  code: 'ack candle before',
  name: 'wary',
  address: 'ballpark fictionalise',
  accountName: 'Checking Account',
};

export const sampleWithFullData: ICarrier = {
  id: 22218,
  code: 'as',
  name: 'ghostwrite',
  address: 'perplex paddle nurture',
  taxCode: 'as carefully',
  bankAccount: 'waver substantial',
  bankName: 'whose broadly',
  accountName: 'Auto Loan Account',
  branchName: 'cautiously streetcar',
};

export const sampleWithNewData: NewCarrier = {
  code: 'readily',
  name: 'shove restfully',
  address: 'instantly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
