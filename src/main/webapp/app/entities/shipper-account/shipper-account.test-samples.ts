import { IShipperAccount, NewShipperAccount } from './shipper-account.model';

export const sampleWithRequiredData: IShipperAccount = {
  id: 'adcafe62-6a57-43f0-b381-619eece9f379',
  balance: 25457.08,
  accountType: 'DEPOSIT',
};

export const sampleWithPartialData: IShipperAccount = {
  id: 'c988948f-3e38-4a70-a80f-39323d134e5b',
  balance: 21904.65,
  accountType: 'DEPOSIT',
};

export const sampleWithFullData: IShipperAccount = {
  id: 'acdf14a2-0971-4d18-bb88-5ace2db35f7a',
  balance: 4735.07,
  accountType: 'DEPOSIT',
};

export const sampleWithNewData: NewShipperAccount = {
  balance: 14171.49,
  accountType: 'DEPOSIT',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
