import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarrierPersonGroup, NewCarrierPersonGroup } from '../carrier-person-group.model';

export type PartialUpdateCarrierPersonGroup = Partial<ICarrierPersonGroup> & Pick<ICarrierPersonGroup, 'id'>;

export type EntityResponseType = HttpResponse<ICarrierPersonGroup>;
export type EntityArrayResponseType = HttpResponse<ICarrierPersonGroup[]>;

@Injectable({ providedIn: 'root' })
export class CarrierPersonGroupService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carrier-person-groups');

  create(carrierPersonGroup: NewCarrierPersonGroup): Observable<EntityResponseType> {
    return this.http.post<ICarrierPersonGroup>(this.resourceUrl, carrierPersonGroup, { observe: 'response' });
  }

  update(carrierPersonGroup: ICarrierPersonGroup): Observable<EntityResponseType> {
    return this.http.put<ICarrierPersonGroup>(
      `${this.resourceUrl}/${this.getCarrierPersonGroupIdentifier(carrierPersonGroup)}`,
      carrierPersonGroup,
      { observe: 'response' },
    );
  }

  partialUpdate(carrierPersonGroup: PartialUpdateCarrierPersonGroup): Observable<EntityResponseType> {
    return this.http.patch<ICarrierPersonGroup>(
      `${this.resourceUrl}/${this.getCarrierPersonGroupIdentifier(carrierPersonGroup)}`,
      carrierPersonGroup,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICarrierPersonGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarrierPersonGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarrierPersonGroupIdentifier(carrierPersonGroup: Pick<ICarrierPersonGroup, 'id'>): string {
    return carrierPersonGroup.id;
  }

  compareCarrierPersonGroup(o1: Pick<ICarrierPersonGroup, 'id'> | null, o2: Pick<ICarrierPersonGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getCarrierPersonGroupIdentifier(o1) === this.getCarrierPersonGroupIdentifier(o2) : o1 === o2;
  }

  addCarrierPersonGroupToCollectionIfMissing<Type extends Pick<ICarrierPersonGroup, 'id'>>(
    carrierPersonGroupCollection: Type[],
    ...carrierPersonGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const carrierPersonGroups: Type[] = carrierPersonGroupsToCheck.filter(isPresent);
    if (carrierPersonGroups.length > 0) {
      const carrierPersonGroupCollectionIdentifiers = carrierPersonGroupCollection.map(carrierPersonGroupItem =>
        this.getCarrierPersonGroupIdentifier(carrierPersonGroupItem),
      );
      const carrierPersonGroupsToAdd = carrierPersonGroups.filter(carrierPersonGroupItem => {
        const carrierPersonGroupIdentifier = this.getCarrierPersonGroupIdentifier(carrierPersonGroupItem);
        if (carrierPersonGroupCollectionIdentifiers.includes(carrierPersonGroupIdentifier)) {
          return false;
        }
        carrierPersonGroupCollectionIdentifiers.push(carrierPersonGroupIdentifier);
        return true;
      });
      return [...carrierPersonGroupsToAdd, ...carrierPersonGroupCollection];
    }
    return carrierPersonGroupCollection;
  }
}
