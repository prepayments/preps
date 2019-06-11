import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';

type EntityResponseType = HttpResponse<IPrepaymentDataEntryFile>;
type EntityArrayResponseType = HttpResponse<IPrepaymentDataEntryFile[]>;

@Injectable({ providedIn: 'root' })
export class PrepaymentDataEntryFileService {
  public resourceUrl = SERVER_API_URL + 'api/prepayment-data-entry-files';

  constructor(protected http: HttpClient) {}

  create(prepaymentDataEntryFile: IPrepaymentDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prepaymentDataEntryFile);
    return this.http
      .post<IPrepaymentDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(prepaymentDataEntryFile: IPrepaymentDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prepaymentDataEntryFile);
    return this.http
      .put<IPrepaymentDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPrepaymentDataEntryFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPrepaymentDataEntryFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(prepaymentDataEntryFile: IPrepaymentDataEntryFile): IPrepaymentDataEntryFile {
    const copy: IPrepaymentDataEntryFile = Object.assign({}, prepaymentDataEntryFile, {
      periodFrom:
        prepaymentDataEntryFile.periodFrom != null && prepaymentDataEntryFile.periodFrom.isValid()
          ? prepaymentDataEntryFile.periodFrom.format(DATE_FORMAT)
          : null,
      periodTo:
        prepaymentDataEntryFile.periodTo != null && prepaymentDataEntryFile.periodTo.isValid()
          ? prepaymentDataEntryFile.periodTo.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.periodFrom = res.body.periodFrom != null ? moment(res.body.periodFrom) : null;
      res.body.periodTo = res.body.periodTo != null ? moment(res.body.periodTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((prepaymentDataEntryFile: IPrepaymentDataEntryFile) => {
        prepaymentDataEntryFile.periodFrom = prepaymentDataEntryFile.periodFrom != null ? moment(prepaymentDataEntryFile.periodFrom) : null;
        prepaymentDataEntryFile.periodTo = prepaymentDataEntryFile.periodTo != null ? moment(prepaymentDataEntryFile.periodTo) : null;
      });
    }
    return res;
  }
}
