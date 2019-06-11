import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';

@Component({
  selector: 'gha-prepayment-data-entry-file-detail',
  templateUrl: './prepayment-data-entry-file-detail.component.html'
})
export class PrepaymentDataEntryFileDetailComponent implements OnInit {
  prepaymentDataEntryFile: IPrepaymentDataEntryFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prepaymentDataEntryFile }) => {
      this.prepaymentDataEntryFile = prepaymentDataEntryFile;
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
