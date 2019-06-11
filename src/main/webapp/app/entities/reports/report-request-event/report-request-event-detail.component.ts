import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';

@Component({
  selector: 'gha-report-request-event-detail',
  templateUrl: './report-request-event-detail.component.html'
})
export class ReportRequestEventDetailComponent implements OnInit {
  reportRequestEvent: IReportRequestEvent;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportRequestEvent }) => {
      this.reportRequestEvent = reportRequestEvent;
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
