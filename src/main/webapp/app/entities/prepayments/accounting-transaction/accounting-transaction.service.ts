import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';

type EntityResponseType = HttpResponse<IAccountingTransaction>;
type EntityArrayResponseType = HttpResponse<IAccountingTransaction[]>;

@Injectable({ providedIn: 'root' })
export class AccountingTransactionService {
  public resourceUrl = SERVER_API_URL + 'api/accounting-transactions';

  constructor(protected http: HttpClient) {}

  create(accountingTransaction: IAccountingTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountingTransaction);
    return this.http
      .post<IAccountingTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountingTransaction: IAccountingTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountingTransaction);
    return this.http
      .put<IAccountingTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccountingTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountingTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(accountingTransaction: IAccountingTransaction): IAccountingTransaction {
    const copy: IAccountingTransaction = Object.assign({}, accountingTransaction, {
      transactionDate:
        accountingTransaction.transactionDate != null && accountingTransaction.transactionDate.isValid()
          ? accountingTransaction.transactionDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionDate = res.body.transactionDate != null ? moment(res.body.transactionDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accountingTransaction: IAccountingTransaction) => {
        accountingTransaction.transactionDate =
          accountingTransaction.transactionDate != null ? moment(accountingTransaction.transactionDate) : null;
      });
    }
    return res;
  }
}
