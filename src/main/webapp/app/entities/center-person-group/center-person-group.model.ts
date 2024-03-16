import { ICenterPerson } from 'app/entities/center-person/center-person.model';

export interface ICenterPersonGroup {
  id: number;
  name?: string | null;
  description?: string | null;
  users?: Pick<ICenterPerson, 'id'>[] | null;
}

export type NewCenterPersonGroup = Omit<ICenterPersonGroup, 'id'> & { id: null };
