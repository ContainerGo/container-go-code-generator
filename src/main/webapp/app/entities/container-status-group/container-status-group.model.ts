import { IContainerStatus } from 'app/entities/container-status/container-status.model';

export interface IContainerStatusGroup {
  id: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  containerStatuses?: Pick<IContainerStatus, 'id'>[] | null;
}

export type NewContainerStatusGroup = Omit<IContainerStatusGroup, 'id'> & { id: null };
