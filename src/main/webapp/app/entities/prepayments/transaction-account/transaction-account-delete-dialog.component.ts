import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';
import { TransactionAccountService } from './transaction-account.service';

@Component({
  selector: 'gha-transaction-account-delete-dialog',
  templateUrl: './transaction-account-delete-dialog.component.html'
})
export class TransactionAccountDeleteDialogComponent {
  transactionAccount: ITransactionAccount;

  constructor(
    protected transactionAccountService: TransactionAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionAccountService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionAccountListModification',
        content: 'Deleted an transactionAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-transaction-account-delete-popup',
  template: ''
})
export class TransactionAccountDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionAccount = transactionAccount;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-account', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-account', { outlets: { popup: null } }]);
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
