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
}

export type NewCarrier = Omit<ICarrier, 'id'> & { id: null };
