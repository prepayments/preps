import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs/internal/Subject';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService} from 'ng-jhipster';
import {NGXLogger} from 'ngx-logger';
import {HttpErrorResponse} from '@angular/common/http';
import {AmortizationReportingService} from 'app/preps/data-export/amortization-entries/amortization-reporting.service';

@Component({
    selector: 'gha-amortization-entries',
    templateUrl: './amortization-entries.component.html',
    styleUrls: ['./amortization-entries.component.scss']
})
export class AmortizationEntriesComponent implements OnInit, OnDestroy {

    dtOptions: DataTables.Settings;
    dtTrigger: Subject<any> = new Subject<any>();

    amortizationEntries$: any[];
    amortizationEntries: any[];

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected jhiAlertService: JhiAlertService,
        protected log: NGXLogger,
        protected amortizationReportingService: AmortizationReportingService
    ) {
        // Its loaded faster here than on initilization
        this.dtOptions = {
            searching: true,
            paging: true,
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            dom: 'Bfrtip',
            buttons: ['copy', 'csv', 'excel', 'pdf', 'print']
        };

        // Still the only way to preload the js files
        this.amortizationReportingService.getEntities().subscribe(
            res => {
                this.amortizationEntries = res.body;
                this.dtTrigger.next();
            },
            (res: HttpErrorResponse) => this.onError(res.message),
            () => {
                this.log.info(`First pass polling of amortization entry items to render with the template...`);
            }
        );
    }

    ngOnInit() {
        this.amortizationReportingService.getEntities().subscribe(
            res => {
                this.amortizationEntries = res.body;
                this.dtTrigger.next();
            },
            (res: HttpErrorResponse) => this.onError(res.message),
            () => {
                this.log.info(`Pulled amortization entry items to render with the template...`);
            }
        );
    }

    /*getEntries(): Observable<EntityArrayResponseType> {
        return this.http.get<IAmortizationEntry[]>(this.resourceUrl, { observe: 'response' }).pipe(
            map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)),
            tap(res => this.log.info(`Fetched ${res.body.length} amortization entry items from url ${this.resourceUrl}`))
        );
    }*/

    /*protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((amortizationEntry: IAmortizationEntry) => {
                amortizationEntry.amortizationDate =
                    amortizationEntry.amortizationDate != null ? moment(amortizationEntry.amortizationDate) : null;
            });
        }
        return res;
    }*/

    protected onError(errorMessage: string) {
        // tell the wide-eyed user
        this.jhiAlertService.error(errorMessage, null, null);
        // log to console
        this.log.error(`Error has occured while fetching amortization entries ${errorMessage}`);
    }

    ngOnDestroy(): void {
        this.dtTrigger.unsubscribe();
        this.amortizationEntries = [];
        this.amortizationEntries$ = [];
    }
}
