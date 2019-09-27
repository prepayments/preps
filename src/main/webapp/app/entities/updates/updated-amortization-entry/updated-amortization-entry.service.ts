import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';

type EntityResponseType = HttpResponse<IUpdatedAmortizationEntry>;
type EntityArrayResponseType = HttpResponse<IUpdatedAmortizationEntry[]>;

@Injectable({ providedIn: 'root' })
export class UpdatedAmortizationEntryService {
  public resourceUrl = SERVER_API_URL + 'api/updated-amortization-entries';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/updated-amortization-entries';

  constructor(protected http: HttpClient) {}

  create(updatedAmortizationEntry: IUpdatedAmortizationEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(updatedAmortizationEntry);
    return this.http
      .post<IUpdatedAmortizationEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(updatedAmortizationEntry: IUpdatedAmortizationEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(updatedAmortizationEntry);
    return this.http
      .put<IUpdatedAmortizationEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUpdatedAmortizationEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUpdatedAmortizationEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUpdatedAmortizationEntry[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(updatedAmortizationEntry: IUpdatedAmortizationEntry): IUpdatedAmortizationEntry {
    const copy: IUpdatedAmortizationEntry = Object.assign({}, updatedAmortizationEntry, {
      amortizationDate:
        updatedAmortizationEntry.amortizationDate != null && updatedAmortizationEntry.amortizationDate.isValid()
          ? updatedAmortizationEntry.amortizationDate.format(DATE_FORMAT)
          : null,
      dateOfUpdate:
        updatedAmortizationEntry.dateOfUpdate != null && updatedAmortizationEntry.dateOfUpdate.isValid()
          ? updatedAmortizationEntry.dateOfUpdate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.amortizationDate = res.body.amortizationDate != null ? moment(res.body.amortizationDate) : null;
      res.body.dateOfUpdate = res.body.dateOfUpdate != null ? moment(res.body.dateOfUpdate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((updatedAmortizationEntry: IUpdatedAmortizationEntry) => {
        updatedAmortizationEntry.amortizationDate =
          updatedAmortizationEntry.amortizationDate != null ? moment(updatedAmortizationEntry.amortizationDate) : null;
        updatedAmortizationEntry.dateOfUpdate =
          updatedAmortizationEntry.dateOfUpdate != null ? moment(updatedAmortizationEntry.dateOfUpdate) : null;
      });
    }
    return res;
  }
}
