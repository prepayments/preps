import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';

type EntityResponseType = HttpResponse<IAmortizationUpload>;
type EntityArrayResponseType = HttpResponse<IAmortizationUpload[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationUploadService {
  public resourceUrl = SERVER_API_URL + 'api/amortization-uploads';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/amortization-uploads';

  constructor(protected http: HttpClient) {}

  create(amortizationUpload: IAmortizationUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationUpload);
    return this.http
      .post<IAmortizationUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(amortizationUpload: IAmortizationUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationUpload);
    return this.http
      .put<IAmortizationUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAmortizationUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationUpload[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationUpload[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(amortizationUpload: IAmortizationUpload): IAmortizationUpload {
    const copy: IAmortizationUpload = Object.assign({}, amortizationUpload, {
      prepaymentTransactionDate:
        amortizationUpload.prepaymentTransactionDate != null && amortizationUpload.prepaymentTransactionDate.isValid()
          ? amortizationUpload.prepaymentTransactionDate.format(DATE_FORMAT)
          : null,
      firstAmortizationDate:
        amortizationUpload.firstAmortizationDate != null && amortizationUpload.firstAmortizationDate.isValid()
          ? amortizationUpload.firstAmortizationDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.prepaymentTransactionDate = res.body.prepaymentTransactionDate != null ? moment(res.body.prepaymentTransactionDate) : null;
      res.body.firstAmortizationDate = res.body.firstAmortizationDate != null ? moment(res.body.firstAmortizationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((amortizationUpload: IAmortizationUpload) => {
        amortizationUpload.prepaymentTransactionDate =
          amortizationUpload.prepaymentTransactionDate != null ? moment(amortizationUpload.prepaymentTransactionDate) : null;
        amortizationUpload.firstAmortizationDate =
          amortizationUpload.firstAmortizationDate != null ? moment(amortizationUpload.firstAmortizationDate) : null;
      });
    }
    return res;
  }
}
