import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';

type EntityResponseType = HttpResponse<IAmortizationUploadFile>;
type EntityArrayResponseType = HttpResponse<IAmortizationUploadFile[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationUploadFileService {
  public resourceUrl = SERVER_API_URL + 'api/app/amortization-upload-files';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/amortization-upload-files';

  constructor(protected http: HttpClient) {}

  create(amortizationUploadFile: IAmortizationUploadFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationUploadFile);
    return this.http
      .post<IAmortizationUploadFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(amortizationUploadFile: IAmortizationUploadFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationUploadFile);
    return this.http
      .put<IAmortizationUploadFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAmortizationUploadFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationUploadFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationUploadFile[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(amortizationUploadFile: IAmortizationUploadFile): IAmortizationUploadFile {
    const copy: IAmortizationUploadFile = Object.assign({}, amortizationUploadFile, {
      periodFrom:
        amortizationUploadFile.periodFrom != null && amortizationUploadFile.periodFrom.isValid()
          ? amortizationUploadFile.periodFrom.format(DATE_FORMAT)
          : null,
      periodTo:
        amortizationUploadFile.periodTo != null && amortizationUploadFile.periodTo.isValid()
          ? amortizationUploadFile.periodTo.format(DATE_FORMAT)
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
      res.body.forEach((amortizationUploadFile: IAmortizationUploadFile) => {
        amortizationUploadFile.periodFrom = amortizationUploadFile.periodFrom != null ? moment(amortizationUploadFile.periodFrom) : null;
        amortizationUploadFile.periodTo = amortizationUploadFile.periodTo != null ? moment(amortizationUploadFile.periodTo) : null;
      });
    }
    return res;
  }
}
