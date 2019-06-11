import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs/index';
import { map } from 'rxjs/operators';
import {IServiceOutlet} from 'app/shared/model/prepayments/service-outlet.model';

type EntityArrayResponseType = HttpResponse<IServiceOutlet[]>;

@Injectable({
    providedIn: 'root'
})
export class ServiceOutletsReportingService {
    public resourceUrl = SERVER_API_URL + '/api/reports/service-outlets-list';
    // public resourceUrl = 'http://5cf509b4ca57690014ab391c.mockapi.io/api/service-outlets';

    constructor(protected http: HttpClient) {}

    getEntities(): Observable<EntityArrayResponseType> {
        return this.http.get<IServiceOutlet[]>(this.resourceUrl, { observe: 'response' });
    }
}
