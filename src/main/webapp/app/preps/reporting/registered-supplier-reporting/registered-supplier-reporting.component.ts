import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiDataUtils} from 'ng-jhipster';
import {NGXLogger} from 'ngx-logger';
import {IRegisteredSupplierReport} from 'app/preps/model/registered-supplier-report.model';

@Component({
  selector: 'gha-registered-supplier-reporting',
  templateUrl: './registered-supplier-reporting.component.html',
  styleUrls: ['./registered-supplier-reporting.component.scss']
})
export class RegisteredSupplierReportingComponent implements OnInit {

    registeredSupplierReport: IRegisteredSupplierReport;
    isSaving: boolean;

    constructor(private activatedRoute: ActivatedRoute, private dataUtils: JhiDataUtils, private log: NGXLogger) {}

    ngOnInit() {
        this.log.debug('Initializing view for registered suppliers report component...');
        this.activatedRoute.data.subscribe(({ registeredSupplierReport }) => {
            this.log.debug(`Successfully fetched registered supplier report from registered supplier report component guard`);
            this.registeredSupplierReport = registeredSupplierReport;
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
