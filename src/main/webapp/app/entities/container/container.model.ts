import dayjs from 'dayjs/esm';
import { IProvice } from 'app/entities/provice/provice.model';
import { IDistrict } from 'app/entities/district/district.model';
import { IWard } from 'app/entities/ward/ward.model';
import { IContainerType } from 'app/entities/container-type/container-type.model';
import { IContainerStatus } from 'app/entities/container-status/container-status.model';
import { ITruckType } from 'app/entities/truck-type/truck-type.model';
import { ITruck } from 'app/entities/truck/truck.model';
import { IContainerOwner } from 'app/entities/container-owner/container-owner.model';
import { ContainerState } from 'app/entities/enumerations/container-state.model';

export interface IContainer {
  id: number;
  contNo?: string | null;
  estimatedPrice?: number | null;
  distance?: number | null;
  desiredPrice?: number | null;
  additionalRequirements?: string | null;
  dropoffContact?: string | null;
  dropoffContactPhone?: string | null;
  dropoffAddress?: string | null;
  dropoffLat?: number | null;
  dropoffLng?: number | null;
  dropoffUntilDate?: dayjs.Dayjs | null;
  state?: keyof typeof ContainerState | null;
  shipperId?: number | null;
  carrierId?: number | null;
  totalWeight?: number | null;
  pickupFromDate?: dayjs.Dayjs | null;
  biddingFromDate?: dayjs.Dayjs | null;
  biddingUntilDate?: dayjs.Dayjs | null;
  dropoffProvice?: Pick<IProvice, 'id'> | null;
  dropoffDistrict?: Pick<IDistrict, 'id'> | null;
  dropoffWard?: Pick<IWard, 'id'> | null;
  type?: Pick<IContainerType, 'id'> | null;
  status?: Pick<IContainerStatus, 'id'> | null;
  truckType?: Pick<ITruckType, 'id'> | null;
  truck?: Pick<ITruck, 'id'> | null;
  owner?: Pick<IContainerOwner, 'id'> | null;
}

export type NewContainer = Omit<IContainer, 'id'> & { id: null };
