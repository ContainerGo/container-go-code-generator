import { IShipper } from 'app/entities/shipper/shipper.model';

export interface IShipperAccount {
  id: number;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  shipper?: Pick<IShipper, 'id'> | null;
}

export type NewShipperAccount = Omit<IShipperAccount, 'id'> & { id: null };
