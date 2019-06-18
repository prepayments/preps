import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { NGXLogger } from 'ngx-logger';
import { IRegisteredSupplierReport } from 'app/preps/model/registered-supplier-report.model';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { ReportUtils } from 'app/preps/reporting/report-utils';

type EntityResponseType = HttpResponse<IRegisteredSupplierReport>;

@Injectable({
  providedIn: 'root'
})
export class RegisteredSupplierReportingService {
  public resourceUrl = SERVER_API_URL + '/api/registered-suppliers-listing-report';

  constructor(private log: NGXLogger, private http: HttpClient) {}

  public getAllSuppliersReport(): Observable<EntityResponseType> {
    this.log.debug(`Fetching registered suppliers report from url : ${this.resourceUrl}... `);

    return this.http
      .get<IRegisteredSupplierReport>(`${this.resourceUrl}`, { observe: 'response' })
      .pipe(catchError(ReportUtils.probablyUnregisteredReport));
  }
}
