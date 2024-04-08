import dayjs from 'dayjs/esm';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';
import { ContractType } from 'app/entities/enumerations/contract-type.model';

export interface IShipper {
  id: string;
  code?: string | null;
  name?: string | null;
  address?: string | null;
  taxCode?: string | null;
  companySize?: number | null;
  paymentType?: keyof typeof PaymentType | null;
  contractType?: keyof typeof ContractType | null;
  contractValidUntil?: dayjs.Dayjs | null;
  isApproved?: boolean | null;
  isBillingInformationComplete?: boolean | null;
  isProfileComplete?: boolean | null;
}

export type NewShipper = Omit<IShipper, 'id'> & { id: null };
