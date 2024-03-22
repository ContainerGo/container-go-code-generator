export interface IContainerType {
  id: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
}

export type NewContainerType = Omit<IContainerType, 'id'> & { id: null };
