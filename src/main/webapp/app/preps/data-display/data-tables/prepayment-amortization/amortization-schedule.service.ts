import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { IAmortizationSchedule } from 'app/preps/model/amortization-schedule';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs/internal/observable/of';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { IBalanceQuery } from 'app/preps/model/balance-query.model';
import * as moment from 'moment';

type EntityArrayResponseType = HttpResponse<IAmortizationSchedule[]>;

@Injectable({
  providedIn: 'root'
})
export class AmortizationScheduleService {
  // public resourceUrl = SERVER_API_URL + '/api/reports/balances/prepayments';
  public resourceUrl = 'http://5d1af26edd81710014e87fd8.mockapi.io/amortization';

  defaultQuery: IBalanceQuery;

  constructor(private http: HttpClient, private log: NGXLogger, private jhiAlertService: JhiAlertService) {
    this.defaultQuery = {
      balanceDate: moment(),
      serviceOutlet: 'all',
      accountName: 'all'
    };
  }

  public query(balanceQuery: IBalanceQuery = this.defaultQuery): Observable<EntityArrayResponseType> {
    // return this.http.post<IAmortizationSchedule[]>(this.resourceUrl, balanceQuery, { observe: 'response' })

    // TODO Add params
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let params = new URLSearchParams();
    params.append('balanceDate', balanceQuery.balanceDate.format('YYYY-MM-DD'));
    params.append('serviceOutlet', balanceQuery.serviceOutlet);
    params.append('accountName', balanceQuery.accountName);

    return this.http
      .get<IAmortizationSchedule[]>(this.resourceUrl, { observe: 'response' })
      .pipe(catchError(this.handleError<HttpResponse<IAmortizationSchedule[]>>('query' /* TODO Add query details*/)));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    // let the user know
    this.jhiAlertService.error('Error occurred while fetching amortization schedule', null, null);

    return (error: any): Observable<T> => {
      this.log.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
