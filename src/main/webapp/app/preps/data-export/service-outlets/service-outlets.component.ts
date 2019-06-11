import {Component, OnDestroy, OnInit} from '@angular/core';
import {IServiceOutlet} from 'app/shared/model/prepayments/service-outlet.model';
import {Subject} from 'rxjs/internal/Subject';
import {JhiAlertService} from 'ng-jhipster';
import {NGXLogger} from 'ngx-logger';
import {ServiceOutletsReportingService} from 'app/preps/data-export/service-outlets/service-outlets-reporting.service';
import {HttpResponse} from '@angular/common/http';

@Component({
    selector: 'gha-service-outlets',
    templateUrl: './service-outlets.component.html',
    styleUrls: ['./service-outlets.component.scss']
})
export class ServiceOutletsComponent implements OnInit, OnDestroy {

    serviceOutlets: IServiceOutlet[];

    dtOptions: DataTables.Settings;
    dtTrigger: Subject<any> = new Subject<any>();

    constructor(
        protected serviceOutletReportingService: ServiceOutletsReportingService,
        protected jhiAlertService: JhiAlertService,
        protected log: NGXLogger
    ) {
        this.dtOptions = {
            searching: true,
            paging: true,
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            dom: 'Bfrtip',
            buttons: ['copy', 'csv', 'excel', 'pdf', 'print']
        };

        this.serviceOutletReportingService.getEntities().subscribe(
            (data: HttpResponse<IServiceOutlet[]>) => {
                this.serviceOutlets = data.body;
                this.dtTrigger.next();
            },
            err => this.onError(err)
        );
    }

    ngOnInit() {
        this.serviceOutletReportingService.getEntities().subscribe(
            (data: HttpResponse<IServiceOutlet[]>) => {
                this.serviceOutlets = data.body;
                this.dtTrigger.next();
            },
            err => this.onError(err)
        );
    }

    protected onError(errorMessage: string) {
        // let user know
        this.jhiAlertService.error(errorMessage, null, null);
        // push to console
        this.log.error(`Error while extracting suppliers from API ${errorMessage}`);
    }

    ngOnDestroy(): void {
        this.serviceOutlets = [];
        this.dtTrigger.unsubscribe();
    }
}
