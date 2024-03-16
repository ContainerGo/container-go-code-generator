import { IShipperAccount, NewShipperAccount } from './shipper-account.model';

export const sampleWithRequiredData: IShipperAccount = {
  id: 21384,
  name: 'cartwheel giving emcee',
  phone: '0262 3239 0766',
};

export const sampleWithPartialData: IShipperAccount = {
  id: 18982,
  name: 'stonewall',
  phone: '0289 2309 0424',
  address: 'underneath spinach',
};

export const sampleWithFullData: IShipperAccount = {
  id: 21065,
  name: 'with',
  phone: '025 6042 5181',
  email: 'PhuTho_Tran@yahoo.com',
  address: 'specialist',
};

export const sampleWithNewData: NewShipperAccount = {
  name: 'rely by',
  phone: '024 8994 5342',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
