import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionAccountDataEntryFile } from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';

type EntityResponseType = HttpResponse<ITransactionAccountDataEntryFile>;
type EntityArrayResponseType = HttpResponse<ITransactionAccountDataEntryFile[]>;

@Injectable({ providedIn: 'root' })
export class TransactionAccountDataEntryFileService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-account-data-entry-files';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transaction-account-data-entry-files';

  constructor(protected http: HttpClient) {}

  create(transactionAccountDataEntryFile: ITransactionAccountDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionAccountDataEntryFile);
    return this.http
      .post<ITransactionAccountDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionAccountDataEntryFile: ITransactionAccountDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionAccountDataEntryFile);
    return this.http
      .put<ITransactionAccountDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionAccountDataEntryFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionAccountDataEntryFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionAccountDataEntryFile[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(transactionAccountDataEntryFile: ITransactionAccountDataEntryFile): ITransactionAccountDataEntryFile {
    const copy: ITransactionAccountDataEntryFile = Object.assign({}, transactionAccountDataEntryFile, {
      periodFrom:
        transactionAccountDataEntryFile.periodFrom != null && transactionAccountDataEntryFile.periodFrom.isValid()
          ? transactionAccountDataEntryFile.periodFrom.format(DATE_FORMAT)
          : null,
      periodTo:
        transactionAccountDataEntryFile.periodTo != null && transactionAccountDataEntryFile.periodTo.isValid()
          ? transactionAccountDataEntryFile.periodTo.format(DATE_FORMAT)
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
      res.body.forEach((transactionAccountDataEntryFile: ITransactionAccountDataEntryFile) => {
        transactionAccountDataEntryFile.periodFrom =
          transactionAccountDataEntryFile.periodFrom != null ? moment(transactionAccountDataEntryFile.periodFrom) : null;
        transactionAccountDataEntryFile.periodTo =
          transactionAccountDataEntryFile.periodTo != null ? moment(transactionAccountDataEntryFile.periodTo) : null;
      });
    }
    return res;
  }
}
