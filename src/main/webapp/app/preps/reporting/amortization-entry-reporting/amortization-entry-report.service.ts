import { Injectable } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { SERVER_API_URL } from 'app/app.constants';
import {IAmortizationEntryReport} from 'app/preps/model/amortization-entry-report.model';

type EntityResponseType = HttpResponse<IAmortizationEntryReport>;

@Injectable({
    providedIn: 'root'
})
export class AmortizationEntryReportService {
    public resourceUrl = SERVER_API_URL + '/api/amortization-entries-report';

    constructor(private log: NGXLogger, private http: HttpClient) {}

    getAllAmortizationsReport(): Observable<EntityResponseType> {
        this.log.debug(`Fetching AmortizationEntryReport from url : ${this.resourceUrl}... `);

        return this.http.get<IAmortizationEntryReport>(`${this.resourceUrl}`, { observe: 'response' });
    }
}
