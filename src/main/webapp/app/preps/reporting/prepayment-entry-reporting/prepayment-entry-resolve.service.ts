import { Injectable } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { NGXLogger } from 'ngx-logger';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import {PrepaymentEntryReportService} from 'app/preps/reporting/prepayment-entry-reporting/prepayment-entry-report.service';
import {IPrepaymentEntryReport} from 'app/preps/model/prepayment-entry-report.model';

@Injectable({
    providedIn: 'root'
})
export class PrepaymentEntryResolveService {
    constructor(private log: NGXLogger, private service: PrepaymentEntryReportService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<IPrepaymentEntryReport> | Observable<IPrepaymentEntryReport> {
        this.log.debug(`Resolve data request for PrepaymentEntryReport for route : ${route.url.toLocaleString()}`);

        return this.service.getAllPrepaymentsReport().pipe(
            filter((response: HttpResponse<IPrepaymentEntryReport>) => response.ok),
            map((prepaymentEntryReport: HttpResponse<IPrepaymentEntryReport>) => prepaymentEntryReport.body)
        );
    }
}
