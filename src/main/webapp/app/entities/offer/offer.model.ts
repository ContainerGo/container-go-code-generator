import dayjs from 'dayjs/esm';
import { IContainer } from 'app/entities/container/container.model';

export interface IOffer {
  id: number;
  message?: string | null;
  pickupFromDate?: dayjs.Dayjs | null;
  pickupUntilDate?: dayjs.Dayjs | null;
  dropoffFromDate?: dayjs.Dayjs | null;
  dropoffUntilDate?: dayjs.Dayjs | null;
  price?: number | null;
  carrierId?: number | null;
  container?: Pick<IContainer, 'id'> | null;
}

export type NewOffer = Omit<IOffer, 'id'> & { id: null };
