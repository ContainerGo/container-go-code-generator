export interface ICenterPersonGroup {
  id: number;
  name?: string | null;
  description?: string | null;
}

export type NewCenterPersonGroup = Omit<ICenterPersonGroup, 'id'> & { id: null };
