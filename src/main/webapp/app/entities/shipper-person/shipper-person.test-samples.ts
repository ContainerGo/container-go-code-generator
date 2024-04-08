import { IShipperPerson, NewShipperPerson } from './shipper-person.model';

export const sampleWithRequiredData: IShipperPerson = {
  id: 'a177f311-243f-4d8c-bfa7-4933ce97c17d',
  name: 'marionberry redden razz',
  phone: '025 4224 2113',
};

export const sampleWithPartialData: IShipperPerson = {
  id: 'c6e6fcba-6459-49e8-bd0d-39702784781f',
  name: 'quiet remainder gah',
  phone: '023 9980 5034',
};

export const sampleWithFullData: IShipperPerson = {
  id: '1a025689-a5d8-4f1f-800b-3990b799a1a6',
  name: 'woot madly',
  phone: '023 9920 8996',
  email: 'BaoBao_7ko26@yahoo.com',
  address: 'doubtfully toward',
};

export const sampleWithNewData: NewShipperPerson = {
  name: 'mesh',
  phone: '021 0563 6148',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
