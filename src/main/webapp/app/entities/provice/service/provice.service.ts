import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProvice, NewProvice } from '../provice.model';

export type PartialUpdateProvice = Partial<IProvice> & Pick<IProvice, 'id'>;

export type EntityResponseType = HttpResponse<IProvice>;
export type EntityArrayResponseType = HttpResponse<IProvice[]>;

@Injectable({ providedIn: 'root' })
export class ProviceService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/provices');

  create(provice: NewProvice): Observable<EntityResponseType> {
    return this.http.post<IProvice>(this.resourceUrl, provice, { observe: 'response' });
  }

  update(provice: IProvice): Observable<EntityResponseType> {
    return this.http.put<IProvice>(`${this.resourceUrl}/${this.getProviceIdentifier(provice)}`, provice, { observe: 'response' });
  }

  partialUpdate(provice: PartialUpdateProvice): Observable<EntityResponseType> {
    return this.http.patch<IProvice>(`${this.resourceUrl}/${this.getProviceIdentifier(provice)}`, provice, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProvice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProvice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProviceIdentifier(provice: Pick<IProvice, 'id'>): string {
    return provice.id;
  }

  compareProvice(o1: Pick<IProvice, 'id'> | null, o2: Pick<IProvice, 'id'> | null): boolean {
    return o1 && o2 ? this.getProviceIdentifier(o1) === this.getProviceIdentifier(o2) : o1 === o2;
  }

  addProviceToCollectionIfMissing<Type extends Pick<IProvice, 'id'>>(
    proviceCollection: Type[],
    ...provicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const provices: Type[] = provicesToCheck.filter(isPresent);
    if (provices.length > 0) {
      const proviceCollectionIdentifiers = proviceCollection.map(proviceItem => this.getProviceIdentifier(proviceItem));
      const provicesToAdd = provices.filter(proviceItem => {
        const proviceIdentifier = this.getProviceIdentifier(proviceItem);
        if (proviceCollectionIdentifiers.includes(proviceIdentifier)) {
          return false;
        }
        proviceCollectionIdentifiers.push(proviceIdentifier);
        return true;
      });
      return [...provicesToAdd, ...proviceCollection];
    }
    return proviceCollection;
  }
}
