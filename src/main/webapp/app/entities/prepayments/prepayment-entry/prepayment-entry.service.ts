import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

type EntityResponseType = HttpResponse<IPrepaymentEntry>;
type EntityArrayResponseType = HttpResponse<IPrepaymentEntry[]>;

@Injectable({ providedIn: 'root' })
export class PrepaymentEntryService {
  public resourceUrl = SERVER_API_URL + 'api/prepayment-entries';

  constructor(protected http: HttpClient) {}

  create(prepaymentEntry: IPrepaymentEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prepaymentEntry);
    return this.http
      .post<IPrepaymentEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(prepaymentEntry: IPrepaymentEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prepaymentEntry);
    return this.http
      .put<IPrepaymentEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPrepaymentEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPrepaymentEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(prepaymentEntry: IPrepaymentEntry): IPrepaymentEntry {
    const copy: IPrepaymentEntry = Object.assign({}, prepaymentEntry, {
      prepaymentDate:
        prepaymentEntry.prepaymentDate != null && prepaymentEntry.prepaymentDate.isValid()
          ? prepaymentEntry.prepaymentDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.prepaymentDate = res.body.prepaymentDate != null ? moment(res.body.prepaymentDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((prepaymentEntry: IPrepaymentEntry) => {
        prepaymentEntry.prepaymentDate = prepaymentEntry.prepaymentDate != null ? moment(prepaymentEntry.prepaymentDate) : null;
      });
    }
    return res;
  }
}
