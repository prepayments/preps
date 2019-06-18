import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { NGXLogger } from 'ngx-logger';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { IPrepaymentEntryReport } from 'app/preps/model/prepayment-entry-report.model';
import { catchError } from 'rxjs/operators';
import { ReportUtils } from 'app/preps/reporting/report-utils';

type EntityResponseType = HttpResponse<IPrepaymentEntryReport>;

@Injectable({
  providedIn: 'root'
})
export class PrepaymentEntryReportService {
  public resourceUrl = SERVER_API_URL + '/api/prepayment-entries-report';

  constructor(private log: NGXLogger, private http: HttpClient) {}

  getAllPrepaymentsReport(): Observable<EntityResponseType> {
    this.log.debug(`Fetching Prepayment entries report from url : ${this.resourceUrl}`);

    return this.http
      .get<IPrepaymentEntryReport>(`${this.resourceUrl}`, { observe: 'response' })
      .pipe(catchError(ReportUtils.probablyUnregisteredReport));
  }
}
