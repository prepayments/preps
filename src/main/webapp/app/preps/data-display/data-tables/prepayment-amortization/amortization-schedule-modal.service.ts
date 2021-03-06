import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AmortizationScheduleQueryComponent } from 'app/preps/data-display/data-tables/prepayment-amortization/amortization-schedule-query/amortization-schedule-query.component';

@Injectable({
  providedIn: 'root'
})
export class AmortizationScheduleModalService {
  private isOpen = false;
  constructor(private modalService: NgbModal) {}

  open(): NgbModalRef {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    const modalRef = this.modalService.open(AmortizationScheduleQueryComponent);
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
