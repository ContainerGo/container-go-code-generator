import { IShipper, NewShipper } from './shipper.model';

export const sampleWithRequiredData: IShipper = {
  id: 8340,
  code: 'ah ah candid',
  name: 'cannibalize',
  address: 'while an',
  paymentType: 'broadly sleet where',
};

export const sampleWithPartialData: IShipper = {
  id: 32158,
  code: 'meh spanish',
  name: 'failing furthermore abnormally',
  address: 'being unlike fond',
  paymentType: 'yearly',
};

export const sampleWithFullData: IShipper = {
  id: 28248,
  code: 'perfectly',
  name: 'agitated remit past',
  address: 'versus',
  taxCode: 'of fully',
  companySize: 24848,
  paymentType: 'agonizing',
  isApproved: true,
  isBillingInformationComplete: true,
  isProfileComplete: false,
};

export const sampleWithNewData: NewShipper = {
  code: 'till dearly colorfully',
  name: 'curly patrimony or',
  address: 'positively private',
  paymentType: 'individualize aha without',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
