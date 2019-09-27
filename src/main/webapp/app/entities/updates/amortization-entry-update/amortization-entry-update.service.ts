import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';

type EntityResponseType = HttpResponse<IAmortizationEntryUpdate>;
type EntityArrayResponseType = HttpResponse<IAmortizationEntryUpdate[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationEntryUpdateService {
  public resourceUrl = SERVER_API_URL + 'api/amortization-entry-updates';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/amortization-entry-updates';

  constructor(protected http: HttpClient) {}

  create(amortizationEntryUpdate: IAmortizationEntryUpdate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationEntryUpdate);
    return this.http
      .post<IAmortizationEntryUpdate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(amortizationEntryUpdate: IAmortizationEntryUpdate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationEntryUpdate);
    return this.http
      .put<IAmortizationEntryUpdate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAmortizationEntryUpdate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationEntryUpdate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationEntryUpdate[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(amortizationEntryUpdate: IAmortizationEntryUpdate): IAmortizationEntryUpdate {
    const copy: IAmortizationEntryUpdate = Object.assign({}, amortizationEntryUpdate, {
      amortizationDate:
        amortizationEntryUpdate.amortizationDate != null && amortizationEntryUpdate.amortizationDate.isValid()
          ? amortizationEntryUpdate.amortizationDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.amortizationDate = res.body.amortizationDate != null ? moment(res.body.amortizationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((amortizationEntryUpdate: IAmortizationEntryUpdate) => {
        amortizationEntryUpdate.amortizationDate =
          amortizationEntryUpdate.amortizationDate != null ? moment(amortizationEntryUpdate.amortizationDate) : null;
      });
    }
    return res;
  }
}
