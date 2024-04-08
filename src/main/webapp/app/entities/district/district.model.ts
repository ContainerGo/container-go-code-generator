export interface IDistrict {
  id: string;
  code?: string | null;
  name?: string | null;
  description?: string | null;
}

export type NewDistrict = Omit<IDistrict, 'id'> & { id: null };
