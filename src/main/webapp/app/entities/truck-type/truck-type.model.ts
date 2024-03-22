export interface ITruckType {
  id: number;
  code?: string | null;
  name?: string | null;
  category?: string | null;
  height?: number | null;
  length?: number | null;
  maxSpeed?: number | null;
  type?: string | null;
  weight?: number | null;
  width?: number | null;
}

export type NewTruckType = Omit<ITruckType, 'id'> & { id: null };
