import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICenterPerson, NewCenterPerson } from '../center-person.model';

export type PartialUpdateCenterPerson = Partial<ICenterPerson> & Pick<ICenterPerson, 'id'>;

export type EntityResponseType = HttpResponse<ICenterPerson>;
export type EntityArrayResponseType = HttpResponse<ICenterPerson[]>;

@Injectable({ providedIn: 'root' })
export class CenterPersonService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/center-people');

  create(centerPerson: NewCenterPerson): Observable<EntityResponseType> {
    return this.http.post<ICenterPerson>(this.resourceUrl, centerPerson, { observe: 'response' });
  }

  update(centerPerson: ICenterPerson): Observable<EntityResponseType> {
    return this.http.put<ICenterPerson>(`${this.resourceUrl}/${this.getCenterPersonIdentifier(centerPerson)}`, centerPerson, {
      observe: 'response',
    });
  }

  partialUpdate(centerPerson: PartialUpdateCenterPerson): Observable<EntityResponseType> {
    return this.http.patch<ICenterPerson>(`${this.resourceUrl}/${this.getCenterPersonIdentifier(centerPerson)}`, centerPerson, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICenterPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICenterPerson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCenterPersonIdentifier(centerPerson: Pick<ICenterPerson, 'id'>): number {
    return centerPerson.id;
  }

  compareCenterPerson(o1: Pick<ICenterPerson, 'id'> | null, o2: Pick<ICenterPerson, 'id'> | null): boolean {
    return o1 && o2 ? this.getCenterPersonIdentifier(o1) === this.getCenterPersonIdentifier(o2) : o1 === o2;
  }

  addCenterPersonToCollectionIfMissing<Type extends Pick<ICenterPerson, 'id'>>(
    centerPersonCollection: Type[],
    ...centerPeopleToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centerPeople: Type[] = centerPeopleToCheck.filter(isPresent);
    if (centerPeople.length > 0) {
      const centerPersonCollectionIdentifiers = centerPersonCollection.map(centerPersonItem =>
        this.getCenterPersonIdentifier(centerPersonItem),
      );
      const centerPeopleToAdd = centerPeople.filter(centerPersonItem => {
        const centerPersonIdentifier = this.getCenterPersonIdentifier(centerPersonItem);
        if (centerPersonCollectionIdentifiers.includes(centerPersonIdentifier)) {
          return false;
        }
        centerPersonCollectionIdentifiers.push(centerPersonIdentifier);
        return true;
      });
      return [...centerPeopleToAdd, ...centerPersonCollection];
    }
    return centerPersonCollection;
  }
}
