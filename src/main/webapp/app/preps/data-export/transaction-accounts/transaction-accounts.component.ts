import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs/internal/Subject';
import {ITransactionAccount} from 'app/shared/model/prepayments/transaction-account.model';
import {TransactionAccountReportingService} from 'app/preps/data-export/transaction-accounts/transaction-account-reporting.service';
import {NGXLogger} from 'ngx-logger';
import {JhiAlertService} from 'ng-jhipster';
import {tap} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';

@Component({
    selector: 'gha-transaction-accounts',
    templateUrl: './transaction-accounts.component.html',
    styleUrls: ['./transaction-accounts.component.scss']
})
export class TransactionAccountsComponent implements OnInit, OnDestroy {
    dtOptions: DataTables.Settings;
    dtTrigger: Subject<any> = new Subject<any>();
    transactionAccounts: ITransactionAccount[];

    constructor(
        protected transactionAccountsReportingService: TransactionAccountReportingService,
        protected jhiAlertService: JhiAlertService,
        protected log: NGXLogger
    ) {
        this.transactionAccountsReportingService
            .getEntities()
            .pipe(tap(res => this.log.info(`Extracted ${res.body.length} transaction account items from the API back end`)))
            .subscribe(
                (data: HttpResponse<ITransactionAccount[]>) => {
                    this.transactionAccounts = data.body;
                    this.dtTrigger.next();
                },
                err => this.onError(err)
            );
    }

    ngOnInit(): void {
        this.dtOptions = {
            searching: true,
            paging: true,
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            dom: 'Bfrtip',
            buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
        };

        this.transactionAccountsReportingService
            .getEntities()
            .pipe(tap(res => this.log.info(`Extracted ${res.body.length} transaction items from the API back end`)))
            .subscribe(
                (data: HttpResponse<ITransactionAccount[]>) => {
                    this.transactionAccounts = data.body;
                    this.dtTrigger.next();
                },
                err => this.onError(err)
            );
    }

    ngOnDestroy(): void {
        this.dtTrigger.unsubscribe();
        this.transactionAccounts = [];
    }

    protected onError(errorMessage: string) {
        // let user know
        this.jhiAlertService.error(errorMessage, null, null);
        // push to console
        this.log.error(`Error while extracting transaction accounts from API ${errorMessage}`);
    }
}
