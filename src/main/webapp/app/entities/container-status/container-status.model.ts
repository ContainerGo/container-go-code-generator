import { IContainerStatusGroup } from 'app/entities/container-status-group/container-status-group.model';

export interface IContainerStatus {
  id: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  group?: Pick<IContainerStatusGroup, 'id'> | null;
}

export type NewContainerStatus = Omit<IContainerStatus, 'id'> & { id: null };
