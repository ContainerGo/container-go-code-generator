import dayjs from 'dayjs/esm';

import { IShipper, NewShipper } from './shipper.model';

export const sampleWithRequiredData: IShipper = {
  id: '436ec432-c15d-4dc2-9bd7-f9973918db51',
  code: 'violation exactly circa',
  name: 'gleefully balloon interesting',
  address: 'culminate of worriedly',
  paymentType: 'CASH_ON_DELIVERY',
  contractType: 'INDIVIDUAL',
};

export const sampleWithPartialData: IShipper = {
  id: 'a40cd63f-3972-454b-a5d2-ba49e2d4c008',
  code: 'tremendously nifty disable',
  name: 'but empower',
  address: 'hence',
  companySize: 7929,
  paymentType: 'CASH_ON_DELIVERY',
  contractType: 'INDIVIDUAL',
  isApproved: false,
  isBillingInformationComplete: true,
};

export const sampleWithFullData: IShipper = {
  id: '43d500c5-1d03-4c35-ad31-01bfa7e5f39e',
  code: 'phooey',
  name: 'prairie',
  address: 'drat enthusiastically',
  taxCode: 'until album considering',
  companySize: 30527,
  paymentType: 'CASH_ON_DELIVERY',
  contractType: 'SIGNED_CONTRACT',
  contractValidUntil: dayjs('2024-03-12T12:32'),
  isApproved: true,
  isBillingInformationComplete: false,
  isProfileComplete: true,
};

export const sampleWithNewData: NewShipper = {
  code: 'gorge mid boring',
  name: 'pine',
  address: 'pace yowza zesty',
  paymentType: 'END_OF_MONTH',
  contractType: 'INDIVIDUAL',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
