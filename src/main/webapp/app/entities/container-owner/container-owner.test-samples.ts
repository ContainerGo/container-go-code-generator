import { IContainerOwner, NewContainerOwner } from './container-owner.model';

export const sampleWithRequiredData: IContainerOwner = {
  id: '48621139-2eb8-49bd-95db-8d89b1afffb9',
  name: 'qua',
};

export const sampleWithPartialData: IContainerOwner = {
  id: '7f7260a4-41f8-4c68-bc10-c199687ecdf4',
  name: 'even naturally vice',
};

export const sampleWithFullData: IContainerOwner = {
  id: 'd70453a3-6cb1-4194-a74d-2db468f747c9',
  name: 'never responsible',
  phone: '021 1300 1450',
  email: 'TrucLien.Truong@gmail.com',
  address: 'yowza',
};

export const sampleWithNewData: NewContainerOwner = {
  name: 'when',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
