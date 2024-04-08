import { IShipperPerson } from 'app/entities/shipper-person/shipper-person.model';

export interface IShipperNotification {
  id: string;
  code?: string | null;
  name?: string | null;
  isEmailNotificationEnabled?: boolean | null;
  isSmsNotificationEnabled?: boolean | null;
  isAppNotificationEnabled?: boolean | null;
  person?: Pick<IShipperPerson, 'id'> | null;
}

export type NewShipperNotification = Omit<IShipperNotification, 'id'> & { id: null };
