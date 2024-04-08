import dayjs from 'dayjs/esm';
import { IContainer } from 'app/entities/container/container.model';

export interface IShipmentPlan {
  id: string;
  estimatedPickupFromDate?: dayjs.Dayjs | null;
  estimatedPickupUntilDate?: dayjs.Dayjs | null;
  estimatedDropoffFromDate?: dayjs.Dayjs | null;
  estimatedDropoffUntilDate?: dayjs.Dayjs | null;
  driverId?: string | null;
  truckId?: string | null;
  container?: Pick<IContainer, 'id'> | null;
}

export type NewShipmentPlan = Omit<IShipmentPlan, 'id'> & { id: null };
