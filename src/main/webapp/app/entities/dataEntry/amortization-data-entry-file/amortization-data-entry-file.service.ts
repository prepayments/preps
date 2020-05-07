import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';

type EntityResponseType = HttpResponse<IAmortizationDataEntryFile>;
type EntityArrayResponseType = HttpResponse<IAmortizationDataEntryFile[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationDataEntryFileService {
  public resourceUrl = SERVER_API_URL + 'api/app/amortization-data-entry-files';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/amortization-data-entry-files';

  constructor(protected http: HttpClient) {}

  create(amortizationDataEntryFile: IAmortizationDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationDataEntryFile);
    return this.http
      .post<IAmortizationDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(amortizationDataEntryFile: IAmortizationDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationDataEntryFile);
    return this.http
      .put<IAmortizationDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAmortizationDataEntryFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationDataEntryFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationDataEntryFile[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(amortizationDataEntryFile: IAmortizationDataEntryFile): IAmortizationDataEntryFile {
    const copy: IAmortizationDataEntryFile = Object.assign({}, amortizationDataEntryFile, {
      periodFrom:
        amortizationDataEntryFile.periodFrom != null && amortizationDataEntryFile.periodFrom.isValid()
          ? amortizationDataEntryFile.periodFrom.format(DATE_FORMAT)
          : null,
      periodTo:
        amortizationDataEntryFile.periodTo != null && amortizationDataEntryFile.periodTo.isValid()
          ? amortizationDataEntryFile.periodTo.format(DATE_FORMAT)
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
      res.body.forEach((amortizationDataEntryFile: IAmortizationDataEntryFile) => {
        amortizationDataEntryFile.periodFrom =
          amortizationDataEntryFile.periodFrom != null ? moment(amortizationDataEntryFile.periodFrom) : null;
        amortizationDataEntryFile.periodTo = amortizationDataEntryFile.periodTo != null ? moment(amortizationDataEntryFile.periodTo) : null;
      });
    }
    return res;
  }
}
