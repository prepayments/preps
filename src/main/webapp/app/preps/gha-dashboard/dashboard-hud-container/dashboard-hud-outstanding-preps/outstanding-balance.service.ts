/**
 * Pulls all information required by this card from the api
 */
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { NGXLogger } from 'ngx-logger';
import { SERVER_API_URL } from 'app/app.constants';
import { IBalanceQuery } from 'app/preps/model/balance-query.model';

@Injectable({
  providedIn: 'root'
})
export class OutstandingBalanceService {
  public resourceCountUrl = SERVER_API_URL + '/api/data/outstanding-prepayments/count';
  public resourceAmountUrl = SERVER_API_URL + '/api/reports/balances/prepayments/amount';

  constructor(private http: HttpClient, private log: NGXLogger) {}

  queryAmount(balanceQuery: IBalanceQuery): Observable<number> {
    return this.http.post<number>(this.resourceAmountUrl, balanceQuery);
  }
}
