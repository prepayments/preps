import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';

type EntityResponseType = HttpResponse<IReportRequestEvent>;
type EntityArrayResponseType = HttpResponse<IReportRequestEvent[]>;

@Injectable({ providedIn: 'root' })
export class ReportRequestEventService {
  public resourceUrl = SERVER_API_URL + 'api/report-request-events';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/report-request-events';

  constructor(protected http: HttpClient) {}

  create(reportRequestEvent: IReportRequestEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportRequestEvent);
    return this.http
      .post<IReportRequestEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reportRequestEvent: IReportRequestEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportRequestEvent);
    return this.http
      .put<IReportRequestEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReportRequestEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReportRequestEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReportRequestEvent[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(reportRequestEvent: IReportRequestEvent): IReportRequestEvent {
    const copy: IReportRequestEvent = Object.assign({}, reportRequestEvent, {
      reportRequestDate:
        reportRequestEvent.reportRequestDate != null && reportRequestEvent.reportRequestDate.isValid()
          ? reportRequestEvent.reportRequestDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.reportRequestDate = res.body.reportRequestDate != null ? moment(res.body.reportRequestDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reportRequestEvent: IReportRequestEvent) => {
        reportRequestEvent.reportRequestDate =
          reportRequestEvent.reportRequestDate != null ? moment(reportRequestEvent.reportRequestDate) : null;
      });
    }
    return res;
  }
}
