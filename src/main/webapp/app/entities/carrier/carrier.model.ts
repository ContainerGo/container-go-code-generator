import dayjs from 'dayjs/esm';
import { ITruck } from 'app/entities/truck/truck.model';
import { ICarrierPerson } from 'app/entities/carrier-person/carrier-person.model';

export interface ICarrier {
  id: number;
  code?: string | null;
  name?: string | null;
  address?: string | null;
  taxCode?: string | null;
  bankAccount?: string | null;
  bankName?: string | null;
  accountName?: string | null;
  branchName?: string | null;
  companySize?: number | null;
  isApproved?: boolean | null;
  vehicles?: number | null;
  shipmentsLeftForDay?: number | null;
  verifiedSince?: dayjs.Dayjs | null;
  trucks?: Pick<ITruck, 'id'>[] | null;
  carrierPeople?: Pick<ICarrierPerson, 'id'>[] | null;
}

export type NewCarrier = Omit<ICarrier, 'id'> & { id: null };
