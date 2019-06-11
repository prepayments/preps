import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITransactionAccountDataEntryFile } from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';

@Component({
  selector: 'gha-transaction-account-data-entry-file-detail',
  templateUrl: './transaction-account-data-entry-file-detail.component.html'
})
export class TransactionAccountDataEntryFileDetailComponent implements OnInit {
  transactionAccountDataEntryFile: ITransactionAccountDataEntryFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionAccountDataEntryFile }) => {
      this.transactionAccountDataEntryFile = transactionAccountDataEntryFile;
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
