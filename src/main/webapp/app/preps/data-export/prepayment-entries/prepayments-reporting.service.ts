import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/index';
import { catchError, map, tap } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import moment = require('moment');
import { of } from 'rxjs/internal/observable/of';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import {IPrepaymentEntry} from 'app/shared/model/prepayments/prepayment-entry.model';

type EntityArrayResponseType = HttpResponse<IPrepaymentEntry[]>;

@Injectable({
    providedIn: 'root'
})
export class PrepaymentsReportingService {
    public resourceUrl = SERVER_API_URL + '/api/reports/prepayment-entries-list';
    // public resourceUrl = 'http://5cf509b4ca57690014ab391c.mockapi.io/api/prepayment-entries';

    constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

    getEntities(): Observable<EntityArrayResponseType> {
        return this.http
            .get<IPrepaymentEntry[]>(this.resourceUrl, { observe: 'response' })
            .pipe(
                tap((res: EntityArrayResponseType) => this.log.info(`fetched : ${res.body.length} prepayment items`)),
                catchError(this.handleError<IPrepaymentEntry[]>('getEntities', []))
            )
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((entity: IPrepaymentEntry) => {
                entity.prepaymentDate = entity.prepaymentDate != null ? moment(entity.prepaymentDate) : null;
            });
        }
        return res;
    }

    private handleError<T>(operation = 'operation', result?: T) {
        // let the user know
        this.jhiAlertService.error('Error occurred while fetching entity : ' + result, null, null);

        return (error: any): Observable<T> => {
            this.log.error(`${operation} failed: ${error.message}`);
            return of(result as T);
        };
    }
}
