import dayjs from 'dayjs/esm';
import { IContainer } from 'app/entities/container/container.model';
import { OfferState } from 'app/entities/enumerations/offer-state.model';

export interface IOffer {
  id: string;
  message?: string | null;
  estimatedPickupFromDate?: dayjs.Dayjs | null;
  estimatedPickupUntilDate?: dayjs.Dayjs | null;
  estimatedDropoffFromDate?: dayjs.Dayjs | null;
  estimatedDropoffUntilDate?: dayjs.Dayjs | null;
  state?: keyof typeof OfferState | null;
  price?: number | null;
  carrierId?: string | null;
  truckId?: string | null;
  container?: Pick<IContainer, 'id'> | null;
}

export type NewOffer = Omit<IOffer, 'id'> & { id: null };
