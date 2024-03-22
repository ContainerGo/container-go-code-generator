export interface ITruck {
  id: number;
  code?: string | null;
  name?: string | null;
}

export type NewTruck = Omit<ITruck, 'id'> & { id: null };
