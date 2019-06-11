import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';

@Component({
  selector: 'gha-service-outlet-data-entry-file-detail',
  templateUrl: './service-outlet-data-entry-file-detail.component.html'
})
export class ServiceOutletDataEntryFileDetailComponent implements OnInit {
  serviceOutletDataEntryFile: IServiceOutletDataEntryFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOutletDataEntryFile }) => {
      this.serviceOutletDataEntryFile = serviceOutletDataEntryFile;
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
