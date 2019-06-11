import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';

type EntityResponseType = HttpResponse<ISupplierDataEntryFile>;
type EntityArrayResponseType = HttpResponse<ISupplierDataEntryFile[]>;

@Injectable({ providedIn: 'root' })
export class SupplierDataEntryFileService {
  public resourceUrl = SERVER_API_URL + 'api/supplier-data-entry-files';

  constructor(protected http: HttpClient) {}

  create(supplierDataEntryFile: ISupplierDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierDataEntryFile);
    return this.http
      .post<ISupplierDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(supplierDataEntryFile: ISupplierDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierDataEntryFile);
    return this.http
      .put<ISupplierDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISupplierDataEntryFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupplierDataEntryFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(supplierDataEntryFile: ISupplierDataEntryFile): ISupplierDataEntryFile {
    const copy: ISupplierDataEntryFile = Object.assign({}, supplierDataEntryFile, {
      periodFrom:
        supplierDataEntryFile.periodFrom != null && supplierDataEntryFile.periodFrom.isValid()
          ? supplierDataEntryFile.periodFrom.format(DATE_FORMAT)
          : null,
      periodTo:
        supplierDataEntryFile.periodTo != null && supplierDataEntryFile.periodTo.isValid()
          ? supplierDataEntryFile.periodTo.format(DATE_FORMAT)
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
      res.body.forEach((supplierDataEntryFile: ISupplierDataEntryFile) => {
        supplierDataEntryFile.periodFrom = supplierDataEntryFile.periodFrom != null ? moment(supplierDataEntryFile.periodFrom) : null;
        supplierDataEntryFile.periodTo = supplierDataEntryFile.periodTo != null ? moment(supplierDataEntryFile.periodTo) : null;
      });
    }
    return res;
  }
}
