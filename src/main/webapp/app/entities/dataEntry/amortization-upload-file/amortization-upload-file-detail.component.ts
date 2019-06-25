import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';

@Component({
  selector: 'gha-amortization-upload-file-detail',
  templateUrl: './amortization-upload-file-detail.component.html'
})
export class AmortizationUploadFileDetailComponent implements OnInit {
  amortizationUploadFile: IAmortizationUploadFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationUploadFile }) => {
      this.amortizationUploadFile = amortizationUploadFile;
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
