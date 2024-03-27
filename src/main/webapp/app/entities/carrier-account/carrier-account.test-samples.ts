import { ICarrierAccount, NewCarrierAccount } from './carrier-account.model';

export const sampleWithRequiredData: ICarrierAccount = {
  id: 1492,
  name: 'within next',
  phone: '026 4688 3585',
};

export const sampleWithPartialData: ICarrierAccount = {
  id: 12192,
  name: 'relieved psst',
  phone: '021 2118 3386',
  address: 'plus excitedly',
};

export const sampleWithFullData: ICarrierAccount = {
  id: 5187,
  name: 'actually hmph',
  phone: '0255 1031 7858',
  email: 'VanTuyen.Nguyen@gmail.com',
  address: 'yesterday converse busy',
};

export const sampleWithNewData: NewCarrierAccount = {
  name: 'the',
  phone: '0237 6938 8809',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
