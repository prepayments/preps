import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';

type EntityResponseType = HttpResponse<IAmortizationUpdateFile>;
type EntityArrayResponseType = HttpResponse<IAmortizationUpdateFile[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationUpdateFileService {
  public resourceUrl = SERVER_API_URL + 'api/notified/amortization-update-files';
  public resourceSearchUrl = SERVER_API_URL + 'api/notified/_search/amortization-update-files';

  constructor(protected http: HttpClient) {}

  create(amortizationUpdateFile: IAmortizationUpdateFile): Observable<EntityResponseType> {
    return this.http.post<IAmortizationUpdateFile>(this.resourceUrl, amortizationUpdateFile, { observe: 'response' });
  }

  update(amortizationUpdateFile: IAmortizationUpdateFile): Observable<EntityResponseType> {
    return this.http.put<IAmortizationUpdateFile>(this.resourceUrl, amortizationUpdateFile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAmortizationUpdateFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAmortizationUpdateFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAmortizationUpdateFile[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
