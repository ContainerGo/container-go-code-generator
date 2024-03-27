import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContainerStatusGroup, NewContainerStatusGroup } from '../container-status-group.model';

export type PartialUpdateContainerStatusGroup = Partial<IContainerStatusGroup> & Pick<IContainerStatusGroup, 'id'>;

export type EntityResponseType = HttpResponse<IContainerStatusGroup>;
export type EntityArrayResponseType = HttpResponse<IContainerStatusGroup[]>;

@Injectable({ providedIn: 'root' })
export class ContainerStatusGroupService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/container-status-groups');

  create(containerStatusGroup: NewContainerStatusGroup): Observable<EntityResponseType> {
    return this.http.post<IContainerStatusGroup>(this.resourceUrl, containerStatusGroup, { observe: 'response' });
  }

  update(containerStatusGroup: IContainerStatusGroup): Observable<EntityResponseType> {
    return this.http.put<IContainerStatusGroup>(
      `${this.resourceUrl}/${this.getContainerStatusGroupIdentifier(containerStatusGroup)}`,
      containerStatusGroup,
      { observe: 'response' },
    );
  }

  partialUpdate(containerStatusGroup: PartialUpdateContainerStatusGroup): Observable<EntityResponseType> {
    return this.http.patch<IContainerStatusGroup>(
      `${this.resourceUrl}/${this.getContainerStatusGroupIdentifier(containerStatusGroup)}`,
      containerStatusGroup,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContainerStatusGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContainerStatusGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContainerStatusGroupIdentifier(containerStatusGroup: Pick<IContainerStatusGroup, 'id'>): number {
    return containerStatusGroup.id;
  }

  compareContainerStatusGroup(o1: Pick<IContainerStatusGroup, 'id'> | null, o2: Pick<IContainerStatusGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getContainerStatusGroupIdentifier(o1) === this.getContainerStatusGroupIdentifier(o2) : o1 === o2;
  }

  addContainerStatusGroupToCollectionIfMissing<Type extends Pick<IContainerStatusGroup, 'id'>>(
    containerStatusGroupCollection: Type[],
    ...containerStatusGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const containerStatusGroups: Type[] = containerStatusGroupsToCheck.filter(isPresent);
    if (containerStatusGroups.length > 0) {
      const containerStatusGroupCollectionIdentifiers = containerStatusGroupCollection.map(containerStatusGroupItem =>
        this.getContainerStatusGroupIdentifier(containerStatusGroupItem),
      );
      const containerStatusGroupsToAdd = containerStatusGroups.filter(containerStatusGroupItem => {
        const containerStatusGroupIdentifier = this.getContainerStatusGroupIdentifier(containerStatusGroupItem);
        if (containerStatusGroupCollectionIdentifiers.includes(containerStatusGroupIdentifier)) {
          return false;
        }
        containerStatusGroupCollectionIdentifiers.push(containerStatusGroupIdentifier);
        return true;
      });
      return [...containerStatusGroupsToAdd, ...containerStatusGroupCollection];
    }
    return containerStatusGroupCollection;
  }
}
