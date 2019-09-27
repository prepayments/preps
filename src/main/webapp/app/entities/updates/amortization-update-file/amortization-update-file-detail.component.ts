import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';

@Component({
  selector: 'gha-amortization-update-file-detail',
  templateUrl: './amortization-update-file-detail.component.html'
})
export class AmortizationUpdateFileDetailComponent implements OnInit {
  amortizationUpdateFile: IAmortizationUpdateFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationUpdateFile }) => {
      this.amortizationUpdateFile = amortizationUpdateFile;
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
