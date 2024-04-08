import { IShipperPersonGroup } from 'app/entities/shipper-person-group/shipper-person-group.model';
import { IShipper } from 'app/entities/shipper/shipper.model';

export interface IShipperPerson {
  id: string;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  group?: Pick<IShipperPersonGroup, 'id'> | null;
  shipper?: Pick<IShipper, 'id'> | null;
}

export type NewShipperPerson = Omit<IShipperPerson, 'id'> & { id: null };
