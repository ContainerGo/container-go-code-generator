import dayjs from 'dayjs/esm';
import { IContainer } from 'app/entities/container/container.model';

export interface IShipmentHistory {
  id: string;
  event?: string | null;
  timestamp?: dayjs.Dayjs | null;
  executedBy?: string | null;
  location?: string | null;
  lat?: number | null;
  lng?: number | null;
  container?: Pick<IContainer, 'id'> | null;
}

export type NewShipmentHistory = Omit<IShipmentHistory, 'id'> & { id: null };
