export interface IDistrict {
  id: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
}

export type NewDistrict = Omit<IDistrict, 'id'> & { id: null };
