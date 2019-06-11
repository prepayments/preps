import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';

type EntityResponseType = HttpResponse<ITransactionAccount>;
type EntityArrayResponseType = HttpResponse<ITransactionAccount[]>;

@Injectable({ providedIn: 'root' })
export class TransactionAccountService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-accounts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transaction-accounts';

  constructor(protected http: HttpClient) {}

  create(transactionAccount: ITransactionAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionAccount);
    return this.http
      .post<ITransactionAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionAccount: ITransactionAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionAccount);
    return this.http
      .put<ITransactionAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionAccount[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(transactionAccount: ITransactionAccount): ITransactionAccount {
    const copy: ITransactionAccount = Object.assign({}, transactionAccount, {
      openingDate:
        transactionAccount.openingDate != null && transactionAccount.openingDate.isValid()
          ? transactionAccount.openingDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.openingDate = res.body.openingDate != null ? moment(res.body.openingDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transactionAccount: ITransactionAccount) => {
        transactionAccount.openingDate = transactionAccount.openingDate != null ? moment(transactionAccount.openingDate) : null;
      });
    }
    return res;
  }
}
