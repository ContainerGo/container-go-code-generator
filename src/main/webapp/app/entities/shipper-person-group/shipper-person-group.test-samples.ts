import { IShipperPersonGroup, NewShipperPersonGroup } from './shipper-person-group.model';

export const sampleWithRequiredData: IShipperPersonGroup = {
  id: '1a630cd2-6c41-43f1-a06f-23028b16d004',
  name: 'forenenst',
};

export const sampleWithPartialData: IShipperPersonGroup = {
  id: '0e9e8396-4ab5-433d-8320-f5a799099835',
  name: 'eek exocrine solidly',
};

export const sampleWithFullData: IShipperPersonGroup = {
  id: '1cd2ec09-9765-42ab-af81-4b3c9a994224',
  name: 'out',
};

export const sampleWithNewData: NewShipperPersonGroup = {
  name: 'thoughtfully',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
