import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BalanceQueryModalComponent } from 'app/preps/gha-balance-query/balance-query-modal.component';

/**
 * This service is used to deploy the BalanceQueryModalComponent as a modal on top of anything
 * happening on the page
 */
@Injectable({
  providedIn: 'root'
})
export class BalanceQueryModalService {
  private isOpen = false;
  constructor(private modalService: NgbModal) {}

  open(): NgbModalRef {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    const modalRef = this.modalService.open(BalanceQueryModalComponent);
    modalRef.result.then(
      result => {
        this.isOpen = false;
      },
      reason => {
        this.isOpen = false;
      }
    );
    return modalRef;
  }
}
