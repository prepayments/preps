import { Injectable } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { NGXLogger } from 'ngx-logger';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import {TransactionAccountsReportService} from 'app/preps/reporting/transaction-account-reporting/transaction-accounts-report.service';
import {ITransactionAccountReport} from 'app/preps/model/transaction-account-report.model';

@Injectable({
    providedIn: 'root'
})
export class TransactionAccountsReportResolveService {
    constructor(private log: NGXLogger, private service: TransactionAccountsReportService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<ITransactionAccountReport> | Observable<ITransactionAccountReport> {
        this.log.debug(`Resolve data request for TransactionAccountReport for route : ${route.url.toLocaleString()}`);

        return this.service.getAllTransactionAccountsReport().pipe(
            filter((response: HttpResponse<ITransactionAccountReport>) => response.ok),
            map((transactionAccountReport: HttpResponse<ITransactionAccountReport>) => transactionAccountReport.body)
        );
    }
}
