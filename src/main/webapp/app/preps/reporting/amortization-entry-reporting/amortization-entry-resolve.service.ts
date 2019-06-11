import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { NGXLogger } from 'ngx-logger';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import {AmortizationEntryReport, IAmortizationEntryReport} from 'app/preps/model/amortization-entry-report.model';
import {AmortizationEntryReportService} from 'app/preps/reporting/amortization-entry-reporting/amortization-entry-report.service';

@Injectable({
    providedIn: 'root'
})
export class AmortizationEntryResolveService implements Resolve<IAmortizationEntryReport> {
    constructor(private log: NGXLogger, private service: AmortizationEntryReportService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<IAmortizationEntryReport> | Observable<IAmortizationEntryReport> {
        this.log.debug(`Resolve data request for AmortizationEntryReport for route : ${route.url.toLocaleString()}`);

        return this.service.getAllAmortizationsReport().pipe(
            filter((response: HttpResponse<AmortizationEntryReport>) => response.ok),
            map((amortizationEntryReport: HttpResponse<AmortizationEntryReport>) => amortizationEntryReport.body)
        );
    }
}
