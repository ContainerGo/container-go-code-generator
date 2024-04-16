import { IShipper } from 'app/entities/shipper/shipper.model';
import { ShipperAccountType } from 'app/entities/enumerations/shipper-account-type.model';

export interface IShipperAccount {
  id: string;
  balance?: number | null;
  accountType?: keyof typeof ShipperAccountType | null;
  shipper?: Pick<IShipper, 'id'> | null;
}

export type NewShipperAccount = Omit<IShipperAccount, 'id'> & { id: null };
