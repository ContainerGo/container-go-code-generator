import dayjs from 'dayjs/esm';
import { IContainer } from 'app/entities/container/container.model';
import { OfferState } from 'app/entities/enumerations/offer-state.model';

export interface IOffer {
  id: number;
  message?: string | null;
  pickupFromDate?: dayjs.Dayjs | null;
  pickupUntilDate?: dayjs.Dayjs | null;
  dropoffFromDate?: dayjs.Dayjs | null;
  dropoffUntilDate?: dayjs.Dayjs | null;
  state?: keyof typeof OfferState | null;
  price?: number | null;
  carrierId?: number | null;
  carrierPersonId?: number | null;
  truckId?: number | null;
  container?: Pick<IContainer, 'id'> | null;
}

export type NewOffer = Omit<IOffer, 'id'> & { id: null };
