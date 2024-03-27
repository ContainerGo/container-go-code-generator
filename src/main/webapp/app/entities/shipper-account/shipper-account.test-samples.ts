import { IShipperAccount, NewShipperAccount } from './shipper-account.model';

export const sampleWithRequiredData: IShipperAccount = {
  id: 24897,
  name: 'yahoo before',
  phone: '021 5998 9692',
};

export const sampleWithPartialData: IShipperAccount = {
  id: 25425,
  name: 'model meh furthermore',
  phone: '022 8023 8376',
  email: 'ThuDuyen25@hotmail.com',
};

export const sampleWithFullData: IShipperAccount = {
  id: 22383,
  name: 'needily',
  phone: '0255 3678 1862',
  email: '7kacThanh43@yahoo.com',
  address: 'rumble knavishly sprat',
};

export const sampleWithNewData: NewShipperAccount = {
  name: 'provided whether',
  phone: '024 1947 7324',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
