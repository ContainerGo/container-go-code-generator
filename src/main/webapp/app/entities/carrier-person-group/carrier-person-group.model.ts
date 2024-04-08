export interface ICarrierPersonGroup {
  id: string;
  name?: string | null;
}

export type NewCarrierPersonGroup = Omit<ICarrierPersonGroup, 'id'> & { id: null };
