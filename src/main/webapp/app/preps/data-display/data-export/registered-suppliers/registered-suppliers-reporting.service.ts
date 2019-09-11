import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs/index';
import { of } from 'rxjs/internal/observable/of';
import { JhiAlertService } from 'ng-jhipster';
import { catchError, tap } from 'rxjs/operators';
import { NGXLogger } from 'ngx-logger';
import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

type EntityArrayResponseType = HttpResponse<IRegisteredSupplier[]>;

@Injectable({
  providedIn: 'root'
})
export class RegisteredSuppliersReportingService {
  public resourceUrl = SERVER_API_URL + '/api/reports/registered-suppliers-list';
  // public resourceUrl = 'http://5cf509b4ca57690014ab391c.mockapi.io/api/registered-suppliers';

  constructor(protected http: HttpClient, protected jhiAlertService: JhiAlertService, protected log: NGXLogger) {}

  getEntities(): Observable<EntityArrayResponseType> {
    return this.http.get<IRegisteredSupplier[]>(this.resourceUrl, { observe: 'response' }).pipe(
      tap((res: EntityArrayResponseType) => {
        this.log.debug('fetched : ' + res.body.length + ' supplier items');
      }, catchError(this.handleError<IPrepaymentEntry[]>('getEntities', [])))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    this.jhiAlertService.error('Error occurred while fetching entity : ' + result, null, null);
    return (error: any): Observable<T> => {
      this.log.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
