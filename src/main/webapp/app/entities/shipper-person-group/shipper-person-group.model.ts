export interface IShipperPersonGroup {
  id: string;
  name?: string | null;
}

export type NewShipperPersonGroup = Omit<IShipperPersonGroup, 'id'> & { id: null };
