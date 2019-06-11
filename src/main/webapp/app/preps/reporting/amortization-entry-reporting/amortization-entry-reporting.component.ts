import { Component, OnInit } from '@angular/core';
import {IAmortizationEntryReport} from 'app/preps/model/amortization-entry-report.model';
import {ActivatedRoute} from '@angular/router';
import {JhiDataUtils} from 'ng-jhipster';
import {NGXLogger} from 'ngx-logger';

@Component({
  selector: 'gha-amortization-entry-reporting',
  templateUrl: './amortization-entry-reporting.component.html',
  styleUrls: ['./amortization-entry-reporting.component.scss']
})
export class AmortizationEntryReportingComponent implements OnInit {
    amortizationEntryReport: IAmortizationEntryReport;
    isSaving: boolean;

    constructor(private activatedRoute: ActivatedRoute, private dataUtils: JhiDataUtils, private log: NGXLogger) {}

    ngOnInit() {
        this.log.debug('Initializing view for amortization report component...');
        this.activatedRoute.data.subscribe(({ amortizationEntryReport }) => {
            this.log.debug(`Successfully fetched report : ${amortizationEntryReport} from amortization-entry component guard`);
            this.amortizationEntryReport = amortizationEntryReport;
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
