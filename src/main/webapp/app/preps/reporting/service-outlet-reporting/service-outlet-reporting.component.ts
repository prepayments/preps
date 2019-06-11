import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiDataUtils} from 'ng-jhipster';
import {NGXLogger} from 'ngx-logger';
import {IServiceOutletReport} from 'app/preps/model/service-outlet-report.model';

@Component({
  selector: 'gha-service-outlet-reporting',
  templateUrl: './service-outlet-reporting.component.html',
  styleUrls: ['./service-outlet-reporting.component.scss']
})
export class ServiceOutletReportingComponent implements OnInit {

    serviceOutletReport: IServiceOutletReport;
    isSaving: boolean;

    constructor(private activatedRoute: ActivatedRoute, private dataUtils: JhiDataUtils, private log: NGXLogger) {}

    ngOnInit() {
        this.log.debug('Initializing view for service outlet report component...');
        this.activatedRoute.data.subscribe(({ serviceOutletReport }) => {
            this.log.debug(`Successfully fetched service outlet report from service-outlet report component guard`);
            this.serviceOutletReport = serviceOutletReport;
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
