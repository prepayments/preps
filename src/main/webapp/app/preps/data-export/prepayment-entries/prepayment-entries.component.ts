import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import {PrepaymentsReportingService} from 'app/preps/data-export/prepayment-entries/prepayments-reporting.service';

@Component({
    selector: 'gha-prepayment-entries',
    templateUrl: './prepayment-entries.component.html',
    styleUrls: ['./prepayment-entries.component.scss']
})
export class PrepaymentEntriesComponent implements OnInit, OnDestroy {
    dtOptions: DataTables.Settings;
    dtTrigger: Subject<any> = new Subject<any>();
    prepaymentEntries$: any[];
    constructor(
        protected activatedRoute: ActivatedRoute,
        protected parseLinks: JhiParseLinks,
        protected router: Router,
        protected jhiAlertService: JhiAlertService,
        private prepaymentsReportingService: PrepaymentsReportingService,
        private log: NGXLogger
    ) {
        this.prepaymentsReportingService.getEntities().subscribe(
            res => {
                this.prepaymentEntries$ = res.body;
                this.dtTrigger.next();
            },
            err => this.onError(err.toString()),
            () => this.log.info(`Extracted ${this.prepaymentEntries$.length} prepayment entities from API`)
        );

        this.dtOptions = {
            searching: true,
            paging: true,
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            dom: 'Bfrtip',
            buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
        };

        this.prepaymentsReportingService.getEntities().subscribe(
            res => {
                this.prepaymentEntries$ = res.body;
                this.dtTrigger.next();
            },
            err => this.onError(err.toString()),
            () => this.log.info(`Extracted ${this.prepaymentEntries$.length} prepayment entities from API`)
        );
    }

    ngOnInit() {
        this.dtOptions = {
            searching: true,
            paging: true,
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            dom: 'Bfrtip',
            buttons: ['copy', 'csv', 'excel', 'pdf', 'print']
        };
    }

    ngOnDestroy(): void {
        this.dtTrigger.unsubscribe();
        this.prepaymentEntries$ = [];
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
        this.log.error(`Error while extracting suppliers from API $errorMessage`);
    }
}
