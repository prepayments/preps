import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionAccountDataEntryFile } from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';
import { TransactionAccountDataEntryFileService } from './transaction-account-data-entry-file.service';

@Component({
  selector: 'gha-transaction-account-data-entry-file-delete-dialog',
  templateUrl: './transaction-account-data-entry-file-delete-dialog.component.html'
})
export class TransactionAccountDataEntryFileDeleteDialogComponent {
  transactionAccountDataEntryFile: ITransactionAccountDataEntryFile;

  constructor(
    protected transactionAccountDataEntryFileService: TransactionAccountDataEntryFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionAccountDataEntryFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionAccountDataEntryFileListModification',
        content: 'Deleted an transactionAccountDataEntryFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-transaction-account-data-entry-file-delete-popup',
  template: ''
})
export class TransactionAccountDataEntryFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionAccountDataEntryFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionAccountDataEntryFileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.transactionAccountDataEntryFile = transactionAccountDataEntryFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-account-data-entry-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-account-data-entry-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
