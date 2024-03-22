export interface IWard {
  id: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
}

export type NewWard = Omit<IWard, 'id'> & { id: null };
