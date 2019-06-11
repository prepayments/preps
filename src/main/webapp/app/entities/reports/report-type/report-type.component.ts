import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReportType } from 'app/shared/model/reports/report-type.model';
import { AccountService } from 'app/core';
import { ReportTypeService } from './report-type.service';

@Component({
  selector: 'gha-report-type',
  templateUrl: './report-type.component.html'
})
export class ReportTypeComponent implements OnInit, OnDestroy {
  reportTypes: IReportType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected reportTypeService: ReportTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.reportTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IReportType[]>) => res.ok),
        map((res: HttpResponse<IReportType[]>) => res.body)
      )
      .subscribe(
        (res: IReportType[]) => {
          this.reportTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInReportTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReportType) {
    return item.id;
  }

  registerChangeInReportTypes() {
    this.eventSubscriber = this.eventManager.subscribe('reportTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
