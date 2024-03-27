import { IContainerOwner, NewContainerOwner } from './container-owner.model';

export const sampleWithRequiredData: IContainerOwner = {
  id: 10984,
  name: 'hopelessly range',
};

export const sampleWithPartialData: IContainerOwner = {
  id: 6029,
  name: 'ugh up',
};

export const sampleWithFullData: IContainerOwner = {
  id: 23573,
  name: 'past angelic traumatic',
  phone: '0220 5618 3221',
  email: 'TuyetHoa_Ha@yahoo.com',
  address: 'plus woot dim',
};

export const sampleWithNewData: NewContainerOwner = {
  name: 'still novel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
