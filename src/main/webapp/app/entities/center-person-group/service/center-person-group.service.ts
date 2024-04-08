import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICenterPersonGroup, NewCenterPersonGroup } from '../center-person-group.model';

export type PartialUpdateCenterPersonGroup = Partial<ICenterPersonGroup> & Pick<ICenterPersonGroup, 'id'>;

export type EntityResponseType = HttpResponse<ICenterPersonGroup>;
export type EntityArrayResponseType = HttpResponse<ICenterPersonGroup[]>;

@Injectable({ providedIn: 'root' })
export class CenterPersonGroupService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/center-person-groups');

  create(centerPersonGroup: NewCenterPersonGroup): Observable<EntityResponseType> {
    return this.http.post<ICenterPersonGroup>(this.resourceUrl, centerPersonGroup, { observe: 'response' });
  }

  update(centerPersonGroup: ICenterPersonGroup): Observable<EntityResponseType> {
    return this.http.put<ICenterPersonGroup>(
      `${this.resourceUrl}/${this.getCenterPersonGroupIdentifier(centerPersonGroup)}`,
      centerPersonGroup,
      { observe: 'response' },
    );
  }

  partialUpdate(centerPersonGroup: PartialUpdateCenterPersonGroup): Observable<EntityResponseType> {
    return this.http.patch<ICenterPersonGroup>(
      `${this.resourceUrl}/${this.getCenterPersonGroupIdentifier(centerPersonGroup)}`,
      centerPersonGroup,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICenterPersonGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICenterPersonGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCenterPersonGroupIdentifier(centerPersonGroup: Pick<ICenterPersonGroup, 'id'>): string {
    return centerPersonGroup.id;
  }

  compareCenterPersonGroup(o1: Pick<ICenterPersonGroup, 'id'> | null, o2: Pick<ICenterPersonGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getCenterPersonGroupIdentifier(o1) === this.getCenterPersonGroupIdentifier(o2) : o1 === o2;
  }

  addCenterPersonGroupToCollectionIfMissing<Type extends Pick<ICenterPersonGroup, 'id'>>(
    centerPersonGroupCollection: Type[],
    ...centerPersonGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centerPersonGroups: Type[] = centerPersonGroupsToCheck.filter(isPresent);
    if (centerPersonGroups.length > 0) {
      const centerPersonGroupCollectionIdentifiers = centerPersonGroupCollection.map(centerPersonGroupItem =>
        this.getCenterPersonGroupIdentifier(centerPersonGroupItem),
      );
      const centerPersonGroupsToAdd = centerPersonGroups.filter(centerPersonGroupItem => {
        const centerPersonGroupIdentifier = this.getCenterPersonGroupIdentifier(centerPersonGroupItem);
        if (centerPersonGroupCollectionIdentifiers.includes(centerPersonGroupIdentifier)) {
          return false;
        }
        centerPersonGroupCollectionIdentifiers.push(centerPersonGroupIdentifier);
        return true;
      });
      return [...centerPersonGroupsToAdd, ...centerPersonGroupCollection];
    }
    return centerPersonGroupCollection;
  }
}
