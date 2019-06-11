import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';

@Component({
  selector: 'gha-supplier-data-entry-file-detail',
  templateUrl: './supplier-data-entry-file-detail.component.html'
})
export class SupplierDataEntryFileDetailComponent implements OnInit {
  supplierDataEntryFile: ISupplierDataEntryFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierDataEntryFile }) => {
      this.supplierDataEntryFile = supplierDataEntryFile;
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
