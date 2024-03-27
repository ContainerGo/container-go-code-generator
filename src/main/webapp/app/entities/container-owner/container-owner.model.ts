export interface IContainerOwner {
  id: number;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
}

export type NewContainerOwner = Omit<IContainerOwner, 'id'> & { id: null };
