export interface IWard {
  id: string;
  code?: string | null;
  name?: string | null;
  description?: string | null;
}

export type NewWard = Omit<IWard, 'id'> & { id: null };
