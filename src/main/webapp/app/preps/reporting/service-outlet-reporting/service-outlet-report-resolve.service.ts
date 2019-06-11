import { Injectable } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { NGXLogger } from 'ngx-logger';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import {ServiceOutletReportService} from 'app/preps/reporting/service-outlet-reporting/service-outlet-report.service';
import {IServiceOutletReport} from 'app/preps/model/service-outlet-report.model';

@Injectable({
    providedIn: 'root'
})
export class ServiceOutletReportResolveService {
    constructor(private log: NGXLogger, private service: ServiceOutletReportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceOutletReport> | Observable<IServiceOutletReport> {
        this.log.debug(`Resolve data request for ServiceOutletReport for route : ${route.url.toLocaleString()}`);

        return this.service.getAllServiceOutletReport().pipe(
            filter((response: HttpResponse<IServiceOutletReport>) => response.ok),
            map((serviceOutletReport: HttpResponse<IServiceOutletReport>) => serviceOutletReport.body)
        );
    }
}
