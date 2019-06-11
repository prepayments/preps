import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';
import { AccountingTransactionService } from './accounting-transaction.service';

@Component({
  selector: 'gha-accounting-transaction-delete-dialog',
  templateUrl: './accounting-transaction-delete-dialog.component.html'
})
export class AccountingTransactionDeleteDialogComponent {
  accountingTransaction: IAccountingTransaction;

  constructor(
    protected accountingTransactionService: AccountingTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountingTransactionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountingTransactionListModification',
        content: 'Deleted an accountingTransaction'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-accounting-transaction-delete-popup',
  template: ''
})
export class AccountingTransactionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountingTransaction }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountingTransactionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.accountingTransaction = accountingTransaction;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/accounting-transaction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/accounting-transaction', { outlets: { popup: null } }]);
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
