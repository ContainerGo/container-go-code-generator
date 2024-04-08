import { ICarrierPersonGroup, NewCarrierPersonGroup } from './carrier-person-group.model';

export const sampleWithRequiredData: ICarrierPersonGroup = {
  id: '438a79ce-583d-49f7-9188-9df18e594e85',
  name: 'legislate supposing hmph',
};

export const sampleWithPartialData: ICarrierPersonGroup = {
  id: '0fb29238-b841-4e66-a3a0-275c249fb3dc',
  name: 'incriminate ugh meh',
};

export const sampleWithFullData: ICarrierPersonGroup = {
  id: '854745d7-0e33-4210-9c84-27c3c7fba23c',
  name: 'provided minus incidentally',
};

export const sampleWithNewData: NewCarrierPersonGroup = {
  name: 'lie drab pungent',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
