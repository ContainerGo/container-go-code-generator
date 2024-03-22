export interface IProvice {
  id: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
}

export type NewProvice = Omit<IProvice, 'id'> & { id: null };
