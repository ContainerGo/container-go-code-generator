import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarrierPerson, NewCarrierPerson } from '../carrier-person.model';

export type PartialUpdateCarrierPerson = Partial<ICarrierPerson> & Pick<ICarrierPerson, 'id'>;

export type EntityResponseType = HttpResponse<ICarrierPerson>;
export type EntityArrayResponseType = HttpResponse<ICarrierPerson[]>;

@Injectable({ providedIn: 'root' })
export class CarrierPersonService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carrier-people');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(carrierPerson: NewCarrierPerson): Observable<EntityResponseType> {
    return this.http.post<ICarrierPerson>(this.resourceUrl, carrierPerson, { observe: 'response' });
  }

  update(carrierPerson: ICarrierPerson): Observable<EntityResponseType> {
    return this.http.put<ICarrierPerson>(`${this.resourceUrl}/${this.getCarrierPersonIdentifier(carrierPerson)}`, carrierPerson, {
      observe: 'response',
    });
  }

  partialUpdate(carrierPerson: PartialUpdateCarrierPerson): Observable<EntityResponseType> {
    return this.http.patch<ICarrierPerson>(`${this.resourceUrl}/${this.getCarrierPersonIdentifier(carrierPerson)}`, carrierPerson, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarrierPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarrierPerson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarrierPersonIdentifier(carrierPerson: Pick<ICarrierPerson, 'id'>): number {
    return carrierPerson.id;
  }

  compareCarrierPerson(o1: Pick<ICarrierPerson, 'id'> | null, o2: Pick<ICarrierPerson, 'id'> | null): boolean {
    return o1 && o2 ? this.getCarrierPersonIdentifier(o1) === this.getCarrierPersonIdentifier(o2) : o1 === o2;
  }

  addCarrierPersonToCollectionIfMissing<Type extends Pick<ICarrierPerson, 'id'>>(
    carrierPersonCollection: Type[],
    ...carrierPeopleToCheck: (Type | null | undefined)[]
  ): Type[] {
    const carrierPeople: Type[] = carrierPeopleToCheck.filter(isPresent);
    if (carrierPeople.length > 0) {
      const carrierPersonCollectionIdentifiers = carrierPersonCollection.map(
        carrierPersonItem => this.getCarrierPersonIdentifier(carrierPersonItem)!,
      );
      const carrierPeopleToAdd = carrierPeople.filter(carrierPersonItem => {
        const carrierPersonIdentifier = this.getCarrierPersonIdentifier(carrierPersonItem);
        if (carrierPersonCollectionIdentifiers.includes(carrierPersonIdentifier)) {
          return false;
        }
        carrierPersonCollectionIdentifiers.push(carrierPersonIdentifier);
        return true;
      });
      return [...carrierPeopleToAdd, ...carrierPersonCollection];
    }
    return carrierPersonCollection;
  }
}
