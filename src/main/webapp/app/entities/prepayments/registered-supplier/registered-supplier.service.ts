import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';

type EntityResponseType = HttpResponse<IRegisteredSupplier>;
type EntityArrayResponseType = HttpResponse<IRegisteredSupplier[]>;

@Injectable({ providedIn: 'root' })
export class RegisteredSupplierService {
  public resourceUrl = SERVER_API_URL + 'api/registered-suppliers';

  constructor(protected http: HttpClient) {}

  create(registeredSupplier: IRegisteredSupplier): Observable<EntityResponseType> {
    return this.http.post<IRegisteredSupplier>(this.resourceUrl, registeredSupplier, { observe: 'response' });
  }

  update(registeredSupplier: IRegisteredSupplier): Observable<EntityResponseType> {
    return this.http.put<IRegisteredSupplier>(this.resourceUrl, registeredSupplier, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegisteredSupplier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegisteredSupplier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
