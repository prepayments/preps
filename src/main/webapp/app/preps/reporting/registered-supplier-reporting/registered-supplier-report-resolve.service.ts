import { Injectable } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { NGXLogger } from 'ngx-logger';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import {RegisteredSupplierReportingService} from 'app/preps/reporting/registered-supplier-reporting/registered-supplier-reporting.service';
import {IRegisteredSupplierReport} from 'app/preps/model/registered-supplier-report.model';

@Injectable({
    providedIn: 'root'
})
export class RegisteredSupplierReportResolveService {
    constructor(private log: NGXLogger, private service: RegisteredSupplierReportingService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<IRegisteredSupplierReport> | Observable<IRegisteredSupplierReport> {
        this.log.debug(`Resolve data request for RegisteredSupplierReport for route : ${route.url.toString()}`);

        return this.service.getAllSuppliersReport().pipe(
            filter((response: HttpResponse<IRegisteredSupplierReport>) => response.ok),
            map((registeredSupplierReport: HttpResponse<IRegisteredSupplierReport>) => registeredSupplierReport.body)
        );
    }
}
