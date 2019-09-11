import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import moment = require('moment');
import { Observable } from 'rxjs/index';
import { map, tap } from 'rxjs/operators';
import { NGXLogger } from 'ngx-logger';
import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';

type EntityArrayResponseType = HttpResponse<ITransactionAccount[]>;

@Injectable({
  providedIn: 'root'
})
export class TransactionAccountReportingService {
  public resourceUrl = SERVER_API_URL + '/api/reports/transaction-accounts-list';
  // public resourceUrl = 'http://5cf509b4ca57690014ab391c.mockapi.io/api/transaction-accounts';

  constructor(protected http: HttpClient, protected log: NGXLogger) {}

  getEntities(): Observable<EntityArrayResponseType> {
    return this.http.get<ITransactionAccount[]>(this.resourceUrl, { observe: 'response' }).pipe(
      map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)),
      tap(res => this.log.info(`Fetched ${res.body.length} accounts items from backend API on ${this.resourceUrl}`))
    );
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((account: ITransactionAccount) => {
        account.openingDate = account.openingDate != null ? moment(account.openingDate) : null;
      });
    }
    return res;
  }
}
