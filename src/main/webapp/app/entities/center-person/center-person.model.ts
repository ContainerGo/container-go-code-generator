import { ICenterPersonGroup } from 'app/entities/center-person-group/center-person-group.model';

export interface ICenterPerson {
  id: string;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  group?: Pick<ICenterPersonGroup, 'id'> | null;
  groups?: Pick<ICenterPersonGroup, 'id'>[] | null;
}

export type NewCenterPerson = Omit<ICenterPerson, 'id'> & { id: null };
