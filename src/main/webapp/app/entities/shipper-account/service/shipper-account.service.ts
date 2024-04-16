import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipperAccount, NewShipperAccount } from '../shipper-account.model';

export type PartialUpdateShipperAccount = Partial<IShipperAccount> & Pick<IShipperAccount, 'id'>;

export type EntityResponseType = HttpResponse<IShipperAccount>;
export type EntityArrayResponseType = HttpResponse<IShipperAccount[]>;

@Injectable({ providedIn: 'root' })
export class ShipperAccountService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipper-accounts');

  create(shipperAccount: NewShipperAccount): Observable<EntityResponseType> {
    return this.http.post<IShipperAccount>(this.resourceUrl, shipperAccount, { observe: 'response' });
  }

  update(shipperAccount: IShipperAccount): Observable<EntityResponseType> {
    return this.http.put<IShipperAccount>(`${this.resourceUrl}/${this.getShipperAccountIdentifier(shipperAccount)}`, shipperAccount, {
      observe: 'response',
    });
  }

  partialUpdate(shipperAccount: PartialUpdateShipperAccount): Observable<EntityResponseType> {
    return this.http.patch<IShipperAccount>(`${this.resourceUrl}/${this.getShipperAccountIdentifier(shipperAccount)}`, shipperAccount, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IShipperAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipperAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipperAccountIdentifier(shipperAccount: Pick<IShipperAccount, 'id'>): string {
    return shipperAccount.id;
  }

  compareShipperAccount(o1: Pick<IShipperAccount, 'id'> | null, o2: Pick<IShipperAccount, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipperAccountIdentifier(o1) === this.getShipperAccountIdentifier(o2) : o1 === o2;
  }

  addShipperAccountToCollectionIfMissing<Type extends Pick<IShipperAccount, 'id'>>(
    shipperAccountCollection: Type[],
    ...shipperAccountsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipperAccounts: Type[] = shipperAccountsToCheck.filter(isPresent);
    if (shipperAccounts.length > 0) {
      const shipperAccountCollectionIdentifiers = shipperAccountCollection.map(shipperAccountItem =>
        this.getShipperAccountIdentifier(shipperAccountItem),
      );
      const shipperAccountsToAdd = shipperAccounts.filter(shipperAccountItem => {
        const shipperAccountIdentifier = this.getShipperAccountIdentifier(shipperAccountItem);
        if (shipperAccountCollectionIdentifiers.includes(shipperAccountIdentifier)) {
          return false;
        }
        shipperAccountCollectionIdentifiers.push(shipperAccountIdentifier);
        return true;
      });
      return [...shipperAccountsToAdd, ...shipperAccountCollection];
    }
    return shipperAccountCollection;
  }
}
