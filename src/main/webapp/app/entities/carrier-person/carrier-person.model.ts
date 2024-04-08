import { ICarrierPersonGroup } from 'app/entities/carrier-person-group/carrier-person-group.model';
import { ICarrier } from 'app/entities/carrier/carrier.model';

export interface ICarrierPerson {
  id: string;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  group?: Pick<ICarrierPersonGroup, 'id'> | null;
  carrier?: Pick<ICarrier, 'id'> | null;
}

export type NewCarrierPerson = Omit<ICarrierPerson, 'id'> & { id: null };
