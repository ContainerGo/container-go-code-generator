import { ITruckType } from 'app/entities/truck-type/truck-type.model';
import { ICarrier } from 'app/entities/carrier/carrier.model';
import { TruckStatus } from 'app/entities/enumerations/truck-status.model';

export interface ITruck {
  id: number;
  code?: string | null;
  name?: string | null;
  model?: string | null;
  manufacturer?: string | null;
  year?: number | null;
  capacity?: number | null;
  status?: keyof typeof TruckStatus | null;
  mileage?: number | null;
  numberPlate?: string | null;
  lat?: number | null;
  lng?: number | null;
  type?: Pick<ITruckType, 'id'> | null;
  carrier?: Pick<ICarrier, 'id'> | null;
}

export type NewTruck = Omit<ITruck, 'id'> & { id: null };
