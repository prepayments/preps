import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { NGXLogger } from 'ngx-logger';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import {ITransactionAccountReport} from 'app/preps/model/transaction-account-report.model';

type EntityResponseType = HttpResponse<ITransactionAccountReport>;

@Injectable({
    providedIn: 'root'
})
export class TransactionAccountsReportService {
    public resourceUrl = SERVER_API_URL + '/api/transaction-accounts-listing-report';

    constructor(private log: NGXLogger, private http: HttpClient) {}

    getAllTransactionAccountsReport(): Observable<EntityResponseType> {
        this.log.debug(`Fetching Transaction Accounts report from url: ${this.resourceUrl}...`);

        return this.http.get<ITransactionAccountReport>(`${this.resourceUrl}`, { observe: 'response' });
    }
}
