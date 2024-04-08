import { IShipperNotification, NewShipperNotification } from './shipper-notification.model';

export const sampleWithRequiredData: IShipperNotification = {
  id: 'e01f79d6-0670-4e9c-a070-0b4b30d03882',
  code: 'musty that',
  name: 'meet',
};

export const sampleWithPartialData: IShipperNotification = {
  id: 'f65c128c-096e-42ff-a579-1000f3f847bb',
  code: 'underneath sociable',
  name: 'jilt gadzooks adulterate',
  isEmailNotificationEnabled: true,
  isAppNotificationEnabled: true,
};

export const sampleWithFullData: IShipperNotification = {
  id: '963f219e-0b8c-450a-a6fa-6e1c4f7959b4',
  code: 'failing and',
  name: 'except dual',
  isEmailNotificationEnabled: false,
  isSmsNotificationEnabled: true,
  isAppNotificationEnabled: true,
};

export const sampleWithNewData: NewShipperNotification = {
  code: 'ironclad',
  name: 'squash subsidence',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
