import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

type EntityResponseType = HttpResponse<IAmortizationEntry>;
type EntityArrayResponseType = HttpResponse<IAmortizationEntry[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationEntryService {
  public resourceUrl = SERVER_API_URL + 'api/amortization-entries';

  constructor(protected http: HttpClient) {}

  create(amortizationEntry: IAmortizationEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationEntry);
    return this.http
      .post<IAmortizationEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(amortizationEntry: IAmortizationEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationEntry);
    return this.http
      .put<IAmortizationEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAmortizationEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(amortizationEntry: IAmortizationEntry): IAmortizationEntry {
    const copy: IAmortizationEntry = Object.assign({}, amortizationEntry, {
      amortizationDate:
        amortizationEntry.amortizationDate != null && amortizationEntry.amortizationDate.isValid()
          ? amortizationEntry.amortizationDate.format(DATE_FORMAT)
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
      res.body.forEach((amortizationEntry: IAmortizationEntry) => {
        amortizationEntry.amortizationDate = amortizationEntry.amortizationDate != null ? moment(amortizationEntry.amortizationDate) : null;
      });
    }
    return res;
  }
}
