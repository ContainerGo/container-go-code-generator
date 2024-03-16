export interface IShipper {
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

export type NewShipper = Omit<IShipper, 'id'> & { id: null };
