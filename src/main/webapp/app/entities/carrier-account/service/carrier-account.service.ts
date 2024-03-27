import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarrierAccount, NewCarrierAccount } from '../carrier-account.model';

export type PartialUpdateCarrierAccount = Partial<ICarrierAccount> & Pick<ICarrierAccount, 'id'>;

export type EntityResponseType = HttpResponse<ICarrierAccount>;
export type EntityArrayResponseType = HttpResponse<ICarrierAccount[]>;

@Injectable({ providedIn: 'root' })
export class CarrierAccountService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carrier-accounts');

  create(carrierAccount: NewCarrierAccount): Observable<EntityResponseType> {
    return this.http.post<ICarrierAccount>(this.resourceUrl, carrierAccount, { observe: 'response' });
  }

  update(carrierAccount: ICarrierAccount): Observable<EntityResponseType> {
    return this.http.put<ICarrierAccount>(`${this.resourceUrl}/${this.getCarrierAccountIdentifier(carrierAccount)}`, carrierAccount, {
      observe: 'response',
    });
  }

  partialUpdate(carrierAccount: PartialUpdateCarrierAccount): Observable<EntityResponseType> {
    return this.http.patch<ICarrierAccount>(`${this.resourceUrl}/${this.getCarrierAccountIdentifier(carrierAccount)}`, carrierAccount, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarrierAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarrierAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarrierAccountIdentifier(carrierAccount: Pick<ICarrierAccount, 'id'>): number {
    return carrierAccount.id;
  }

  compareCarrierAccount(o1: Pick<ICarrierAccount, 'id'> | null, o2: Pick<ICarrierAccount, 'id'> | null): boolean {
    return o1 && o2 ? this.getCarrierAccountIdentifier(o1) === this.getCarrierAccountIdentifier(o2) : o1 === o2;
  }

  addCarrierAccountToCollectionIfMissing<Type extends Pick<ICarrierAccount, 'id'>>(
    carrierAccountCollection: Type[],
    ...carrierAccountsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const carrierAccounts: Type[] = carrierAccountsToCheck.filter(isPresent);
    if (carrierAccounts.length > 0) {
      const carrierAccountCollectionIdentifiers = carrierAccountCollection.map(carrierAccountItem =>
        this.getCarrierAccountIdentifier(carrierAccountItem),
      );
      const carrierAccountsToAdd = carrierAccounts.filter(carrierAccountItem => {
        const carrierAccountIdentifier = this.getCarrierAccountIdentifier(carrierAccountItem);
        if (carrierAccountCollectionIdentifiers.includes(carrierAccountIdentifier)) {
          return false;
        }
        carrierAccountCollectionIdentifiers.push(carrierAccountIdentifier);
        return true;
      });
      return [...carrierAccountsToAdd, ...carrierAccountCollection];
    }
    return carrierAccountCollection;
  }
}
