import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { NGXLogger } from 'ngx-logger';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs/index';
import { IPrepaymentTimeBalance } from 'app/preps/model/prepayment-time-balance.model';
import { of } from 'rxjs/internal/observable/of';
import { BalanceQuery, IBalanceQuery } from 'app/preps/model/balance-query.model';
import moment = require('moment');

type EntityArrayResponseType = HttpResponse<IPrepaymentTimeBalance[]>;

@Injectable({
  providedIn: 'root'
})
export class PrepaymentTimeBalanceService {
  public resourceUrl = SERVER_API_URL + '/api/reports/balances/prepayments';

  constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

  getEntities(
    balanceQuery: IBalanceQuery = new BalanceQuery({
      balanceDate: moment('2019-01-01', 'YYYY-MM-DD'),
      accountName: 'PREPAYMENT ACCOUNT',
      serviceOutlet: '100'
    })
  ): Observable<EntityArrayResponseType> {
    this.log.info(`Pulling data for prepayment balances as at the date: ${balanceQuery.balanceDate}`);
    return this.http
      .get<IPrepaymentTimeBalance[]>(this.resourceUrl + balanceQuery.balanceDate, { observe: 'response' })
      .pipe(
        tap((res: EntityArrayResponseType) => this.log.info(`fetched : ${res.body.length} prepayment balance items`)),
        catchError(this.handleError<IPrepaymentTimeBalance[]>('getEntities', []))
      )
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entity: IPrepaymentTimeBalance) => {
        entity.prepaymentDate = entity.prepaymentDate != null ? moment(entity.prepaymentDate) : null;
      });
    }
    return res;
  }

  private handleError<T>(operation = 'operation', result?: T) {
    // let the user know
    this.jhiAlertService.error('Error occurred while fetching prepayment balances', null, null);

    return (error: any): Observable<T> => {
      this.log.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
