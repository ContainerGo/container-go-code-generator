import { ICarrier } from 'app/entities/carrier/carrier.model';

export interface ICarrierAccount {
  id: number;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  carrier?: Pick<ICarrier, 'id'> | null;
}

export type NewCarrierAccount = Omit<ICarrierAccount, 'id'> & { id: null };
