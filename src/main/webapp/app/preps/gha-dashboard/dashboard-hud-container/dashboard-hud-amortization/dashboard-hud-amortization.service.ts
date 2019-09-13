import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { SERVER_API_URL } from 'app/app.constants';
import { IBalanceQuery } from 'app/preps/model/balance-query.model';
import { NGXLogger } from 'ngx-logger';

@Injectable({
  providedIn: 'root'
})
export class DashboardHudAmortizationService {
  public resourceAmountUrl = SERVER_API_URL + '/api/data/amortization-schedule/amount';

  constructor(private http: HttpClient, private log: NGXLogger) {}

  queryAmount(balanceQuery: IBalanceQuery): Observable<number> {
    return this.http.post<number>(this.resourceAmountUrl, balanceQuery);
  }
}
