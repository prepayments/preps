import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';

type EntityResponseType = HttpResponse<IServiceOutletDataEntryFile>;
type EntityArrayResponseType = HttpResponse<IServiceOutletDataEntryFile[]>;

@Injectable({ providedIn: 'root' })
export class ServiceOutletDataEntryFileService {
  public resourceUrl = SERVER_API_URL + 'api/service-outlet-data-entry-files';

  constructor(protected http: HttpClient) {}

  create(serviceOutletDataEntryFile: IServiceOutletDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceOutletDataEntryFile);
    return this.http
      .post<IServiceOutletDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceOutletDataEntryFile: IServiceOutletDataEntryFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceOutletDataEntryFile);
    return this.http
      .put<IServiceOutletDataEntryFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceOutletDataEntryFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceOutletDataEntryFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceOutletDataEntryFile: IServiceOutletDataEntryFile): IServiceOutletDataEntryFile {
    const copy: IServiceOutletDataEntryFile = Object.assign({}, serviceOutletDataEntryFile, {
      periodFrom:
        serviceOutletDataEntryFile.periodFrom != null && serviceOutletDataEntryFile.periodFrom.isValid()
          ? serviceOutletDataEntryFile.periodFrom.format(DATE_FORMAT)
          : null,
      periodTo:
        serviceOutletDataEntryFile.periodTo != null && serviceOutletDataEntryFile.periodTo.isValid()
          ? serviceOutletDataEntryFile.periodTo.format(DATE_FORMAT)
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
      res.body.forEach((serviceOutletDataEntryFile: IServiceOutletDataEntryFile) => {
        serviceOutletDataEntryFile.periodFrom =
          serviceOutletDataEntryFile.periodFrom != null ? moment(serviceOutletDataEntryFile.periodFrom) : null;
        serviceOutletDataEntryFile.periodTo =
          serviceOutletDataEntryFile.periodTo != null ? moment(serviceOutletDataEntryFile.periodTo) : null;
      });
    }
    return res;
  }
}
