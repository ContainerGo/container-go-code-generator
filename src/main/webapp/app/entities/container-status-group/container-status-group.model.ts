export interface IContainerStatusGroup {
  id: string;
  code?: string | null;
  name?: string | null;
  description?: string | null;
}

export type NewContainerStatusGroup = Omit<IContainerStatusGroup, 'id'> & { id: null };
