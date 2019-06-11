import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';

@Component({
  selector: 'gha-amortization-data-entry-file-detail',
  templateUrl: './amortization-data-entry-file-detail.component.html'
})
export class AmortizationDataEntryFileDetailComponent implements OnInit {
  amortizationDataEntryFile: IAmortizationDataEntryFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationDataEntryFile }) => {
      this.amortizationDataEntryFile = amortizationDataEntryFile;
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
