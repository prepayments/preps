import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportType } from 'app/shared/model/reports/report-type.model';

@Component({
  selector: 'gha-report-type-detail',
  templateUrl: './report-type-detail.component.html'
})
export class ReportTypeDetailComponent implements OnInit {
  reportType: IReportType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportType }) => {
      this.reportType = reportType;
    });
  }

  previousState() {
    window.history.back();
  }
}
