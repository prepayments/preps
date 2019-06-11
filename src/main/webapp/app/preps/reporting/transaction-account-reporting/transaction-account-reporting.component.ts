import { Component, OnInit } from '@angular/core';
import {ITransactionAccountReport} from 'app/preps/model/transaction-account-report.model';
import {ActivatedRoute} from '@angular/router';
import {JhiDataUtils} from 'ng-jhipster';
import {NGXLogger} from 'ngx-logger';

@Component({
  selector: 'gha-transaction-account-reporting',
  templateUrl: './transaction-account-reporting.component.html',
  styleUrls: ['./transaction-account-reporting.component.scss']
})
export class TransactionAccountReportingComponent implements OnInit {

    transactionAccountReport: ITransactionAccountReport;
    isSaving: boolean;

    constructor(private activatedRoute: ActivatedRoute, private dataUtils: JhiDataUtils, private log: NGXLogger) {}

    ngOnInit() {
        this.log.debug('Initializing view for transaction account report component...');
        this.activatedRoute.data.subscribe(({ transactionAccountReport }) => {
            this.log.debug(`Successfully fetched transaction account report from transaction account report component guard`);
            this.transactionAccountReport = transactionAccountReport;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    previousState() {
        window.history.back();
    }
}
