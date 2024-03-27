import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContainerType, NewContainerType } from '../container-type.model';

export type PartialUpdateContainerType = Partial<IContainerType> & Pick<IContainerType, 'id'>;

export type EntityResponseType = HttpResponse<IContainerType>;
export type EntityArrayResponseType = HttpResponse<IContainerType[]>;

@Injectable({ providedIn: 'root' })
export class ContainerTypeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/container-types');

  create(containerType: NewContainerType): Observable<EntityResponseType> {
    return this.http.post<IContainerType>(this.resourceUrl, containerType, { observe: 'response' });
  }

  update(containerType: IContainerType): Observable<EntityResponseType> {
    return this.http.put<IContainerType>(`${this.resourceUrl}/${this.getContainerTypeIdentifier(containerType)}`, containerType, {
      observe: 'response',
    });
  }

  partialUpdate(containerType: PartialUpdateContainerType): Observable<EntityResponseType> {
    return this.http.patch<IContainerType>(`${this.resourceUrl}/${this.getContainerTypeIdentifier(containerType)}`, containerType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContainerType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContainerType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContainerTypeIdentifier(containerType: Pick<IContainerType, 'id'>): number {
    return containerType.id;
  }

  compareContainerType(o1: Pick<IContainerType, 'id'> | null, o2: Pick<IContainerType, 'id'> | null): boolean {
    return o1 && o2 ? this.getContainerTypeIdentifier(o1) === this.getContainerTypeIdentifier(o2) : o1 === o2;
  }

  addContainerTypeToCollectionIfMissing<Type extends Pick<IContainerType, 'id'>>(
    containerTypeCollection: Type[],
    ...containerTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const containerTypes: Type[] = containerTypesToCheck.filter(isPresent);
    if (containerTypes.length > 0) {
      const containerTypeCollectionIdentifiers = containerTypeCollection.map(containerTypeItem =>
        this.getContainerTypeIdentifier(containerTypeItem),
      );
      const containerTypesToAdd = containerTypes.filter(containerTypeItem => {
        const containerTypeIdentifier = this.getContainerTypeIdentifier(containerTypeItem);
        if (containerTypeCollectionIdentifiers.includes(containerTypeIdentifier)) {
          return false;
        }
        containerTypeCollectionIdentifiers.push(containerTypeIdentifier);
        return true;
      });
      return [...containerTypesToAdd, ...containerTypeCollection];
    }
    return containerTypeCollection;
  }
}
