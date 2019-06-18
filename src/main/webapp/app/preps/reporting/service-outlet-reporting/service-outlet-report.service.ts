import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { NGXLogger } from 'ngx-logger';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { IServiceOutletReport } from 'app/preps/model/service-outlet-report.model';
import { catchError } from 'rxjs/operators';
import { ReportUtils } from 'app/preps/reporting/report-utils';

type EntityResponseType = HttpResponse<IServiceOutletReport>;

@Injectable({
  providedIn: 'root'
})
export class ServiceOutletReportService {
  public resourceUrl = SERVER_API_URL + '/api/service-outlets-listing-report';

  constructor(private log: NGXLogger, private http: HttpClient) {}

  getAllServiceOutletReport(): Observable<EntityResponseType> {
    this.log.debug(`Fetching Service outlets report from url ${this.resourceUrl}...`);

    return this.http
      .get<IServiceOutletReport>(`${this.resourceUrl}`, { observe: 'response' })
      .pipe(catchError(ReportUtils.probablyUnregisteredReport));
  }
}
