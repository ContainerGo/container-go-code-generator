import { IShipper } from 'app/entities/shipper/shipper.model';

export interface IShipperPerson {
  id: number;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  shipper?: Pick<IShipper, 'id'> | null;
}

export type NewShipperPerson = Omit<IShipperPerson, 'id'> & { id: null };
