export interface ICenterPersonGroup {
  id: string;
  name?: string | null;
}

export type NewCenterPersonGroup = Omit<ICenterPersonGroup, 'id'> & { id: null };
