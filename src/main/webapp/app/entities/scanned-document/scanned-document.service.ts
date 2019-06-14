import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScannedDocument } from 'app/shared/model/scanned-document.model';

type EntityResponseType = HttpResponse<IScannedDocument>;
type EntityArrayResponseType = HttpResponse<IScannedDocument[]>;

@Injectable({ providedIn: 'root' })
export class ScannedDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/scanned-documents';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/scanned-documents';

  constructor(protected http: HttpClient) {}

  create(scannedDocument: IScannedDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scannedDocument);
    return this.http
      .post<IScannedDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(scannedDocument: IScannedDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scannedDocument);
    return this.http
      .put<IScannedDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IScannedDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScannedDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScannedDocument[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(scannedDocument: IScannedDocument): IScannedDocument {
    const copy: IScannedDocument = Object.assign({}, scannedDocument, {
      transactionDate:
        scannedDocument.transactionDate != null && scannedDocument.transactionDate.isValid()
          ? scannedDocument.transactionDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionDate = res.body.transactionDate != null ? moment(res.body.transactionDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((scannedDocument: IScannedDocument) => {
        scannedDocument.transactionDate = scannedDocument.transactionDate != null ? moment(scannedDocument.transactionDate) : null;
      });
    }
    return res;
  }
}
