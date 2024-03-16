import { ICarrierAccount, NewCarrierAccount } from './carrier-account.model';

export const sampleWithRequiredData: ICarrierAccount = {
  id: 28631,
  name: 'likeness spanish',
  phone: '024 2363 6740',
};

export const sampleWithPartialData: ICarrierAccount = {
  id: 24373,
  name: 'contributor kiss',
  phone: '028 4563 6760',
};

export const sampleWithFullData: ICarrierAccount = {
  id: 27345,
  name: 'lumpy',
  phone: '028 4013 4839',
  email: 'MongQuynh.Le84@hotmail.com',
  address: 'yippee present',
};

export const sampleWithNewData: NewCarrierAccount = {
  name: 'reheat opposite',
  phone: '029 5621 6640',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
