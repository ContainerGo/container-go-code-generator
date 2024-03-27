import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContainerOwner, NewContainerOwner } from '../container-owner.model';

export type PartialUpdateContainerOwner = Partial<IContainerOwner> & Pick<IContainerOwner, 'id'>;

export type EntityResponseType = HttpResponse<IContainerOwner>;
export type EntityArrayResponseType = HttpResponse<IContainerOwner[]>;

@Injectable({ providedIn: 'root' })
export class ContainerOwnerService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/container-owners');

  create(containerOwner: NewContainerOwner): Observable<EntityResponseType> {
    return this.http.post<IContainerOwner>(this.resourceUrl, containerOwner, { observe: 'response' });
  }

  update(containerOwner: IContainerOwner): Observable<EntityResponseType> {
    return this.http.put<IContainerOwner>(`${this.resourceUrl}/${this.getContainerOwnerIdentifier(containerOwner)}`, containerOwner, {
      observe: 'response',
    });
  }

  partialUpdate(containerOwner: PartialUpdateContainerOwner): Observable<EntityResponseType> {
    return this.http.patch<IContainerOwner>(`${this.resourceUrl}/${this.getContainerOwnerIdentifier(containerOwner)}`, containerOwner, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContainerOwner>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContainerOwner[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContainerOwnerIdentifier(containerOwner: Pick<IContainerOwner, 'id'>): number {
    return containerOwner.id;
  }

  compareContainerOwner(o1: Pick<IContainerOwner, 'id'> | null, o2: Pick<IContainerOwner, 'id'> | null): boolean {
    return o1 && o2 ? this.getContainerOwnerIdentifier(o1) === this.getContainerOwnerIdentifier(o2) : o1 === o2;
  }

  addContainerOwnerToCollectionIfMissing<Type extends Pick<IContainerOwner, 'id'>>(
    containerOwnerCollection: Type[],
    ...containerOwnersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const containerOwners: Type[] = containerOwnersToCheck.filter(isPresent);
    if (containerOwners.length > 0) {
      const containerOwnerCollectionIdentifiers = containerOwnerCollection.map(containerOwnerItem =>
        this.getContainerOwnerIdentifier(containerOwnerItem),
      );
      const containerOwnersToAdd = containerOwners.filter(containerOwnerItem => {
        const containerOwnerIdentifier = this.getContainerOwnerIdentifier(containerOwnerItem);
        if (containerOwnerCollectionIdentifiers.includes(containerOwnerIdentifier)) {
          return false;
        }
        containerOwnerCollectionIdentifiers.push(containerOwnerIdentifier);
        return true;
      });
      return [...containerOwnersToAdd, ...containerOwnerCollection];
    }
    return containerOwnerCollection;
  }
}
