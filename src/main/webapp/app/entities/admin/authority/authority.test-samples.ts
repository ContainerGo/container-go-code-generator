import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '9cea1850-8d52-4b10-9d53-240a908980d7',
};

export const sampleWithPartialData: IAuthority = {
  name: '20f24c07-1e4e-472b-a515-2ae1250cc8d9',
};

export const sampleWithFullData: IAuthority = {
  name: 'bbe2b6b4-d54a-46c6-816e-b1ebdd71c931',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
