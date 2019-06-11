import { Component, OnInit } from '@angular/core';
import {IPrepaymentEntryReport} from 'app/preps/model/prepayment-entry-report.model';
import {ActivatedRoute} from '@angular/router';
import {JhiDataUtils} from 'ng-jhipster';
import {NGXLogger} from 'ngx-logger';

@Component({
  selector: 'gha-prepayment-entry-reporting',
  templateUrl: './prepayment-entry-reporting.component.html',
  styleUrls: ['./prepayment-entry-reporting.component.scss']
})
export class PrepaymentEntryReportingComponent implements OnInit {
    prepaymentEntryReport: IPrepaymentEntryReport;
    isSaving: boolean;

    constructor(private activatedRoute: ActivatedRoute, private dataUtils: JhiDataUtils, private log: NGXLogger) {}

    ngOnInit() {
        this.log.debug('Initializing view for prepayments report component...');
        this.activatedRoute.data.subscribe(({ prepaymentEntryReport }) => {
            this.log.debug(`Successfully fetched report : ${prepaymentEntryReport} from prepayment-entry component guard`);
            this.prepaymentEntryReport = prepaymentEntryReport;
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
