import { IContainerStatusGroup, NewContainerStatusGroup } from './container-status-group.model';

export const sampleWithRequiredData: IContainerStatusGroup = {
  id: 'bb51b985-ffe0-42b5-a35a-1ebb33789a66',
  code: 'huzzah acetate',
  name: 'jealous neighbor network',
};

export const sampleWithPartialData: IContainerStatusGroup = {
  id: '4e998686-2a2d-4fad-9c78-304b544d1966',
  code: 'lest concerning',
  name: 'duh',
  description: 'marvelous',
};

export const sampleWithFullData: IContainerStatusGroup = {
  id: 'db434a50-1e52-4b77-a79f-17268b17fee9',
  code: 'busy attached',
  name: 'wind supposing huzzah',
  description: 'waste paint since',
};

export const sampleWithNewData: NewContainerStatusGroup = {
  code: 'ouch',
  name: 'regarding weakly barring',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
