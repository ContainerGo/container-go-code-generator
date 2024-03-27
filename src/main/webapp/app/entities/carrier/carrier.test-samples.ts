import dayjs from 'dayjs/esm';

import { ICarrier, NewCarrier } from './carrier.model';

export const sampleWithRequiredData: ICarrier = {
  id: 27880,
  code: 'affectionate usefully under',
  name: 'anonymize',
  address: 'canonise',
};

export const sampleWithPartialData: ICarrier = {
  id: 7242,
  code: 'oddball prickly',
  name: 'circulate mountain',
  address: 'browbeat whose like',
  taxCode: 'likewise fantastic dust',
  vehicles: 21767,
  verifiedSince: dayjs('2024-03-12T14:52'),
};

export const sampleWithFullData: ICarrier = {
  id: 17006,
  code: 'lyre for medicine',
  name: 'happy',
  address: 'though',
  taxCode: 'defensive',
  bankAccount: 'elaborate',
  bankName: 'er',
  accountName: 'Savings Account',
  branchName: 'dislocate',
  companySize: 18311,
  isApproved: false,
  vehicles: 29041,
  shipmentsLeftForDay: 5361,
  verifiedSince: dayjs('2024-03-13T07:43'),
};

export const sampleWithNewData: NewCarrier = {
  code: 'if',
  name: 'ambassador lament concerning',
  address: 'boo boohoo immediately',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
