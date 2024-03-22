import { IShipperPerson } from 'app/entities/shipper-person/shipper-person.model';

export interface IShipper {
  id: number;
  code?: string | null;
  name?: string | null;
  address?: string | null;
  taxCode?: string | null;
  companySize?: number | null;
  paymentType?: string | null;
  isApproved?: boolean | null;
  isBillingInformationComplete?: boolean | null;
  isProfileComplete?: boolean | null;
  shipperPeople?: Pick<IShipperPerson, 'id'>[] | null;
}

export type NewShipper = Omit<IShipper, 'id'> & { id: null };
