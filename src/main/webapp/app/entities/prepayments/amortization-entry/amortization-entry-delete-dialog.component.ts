import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';
import { AmortizationEntryService } from './amortization-entry.service';

@Component({
  selector: 'gha-amortization-entry-delete-dialog',
  templateUrl: './amortization-entry-delete-dialog.component.html'
})
export class AmortizationEntryDeleteDialogComponent {
  amortizationEntry: IAmortizationEntry;

  constructor(
    protected amortizationEntryService: AmortizationEntryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amortizationEntryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amortizationEntryListModification',
        content: 'Deleted an amortizationEntry'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-amortization-entry-delete-popup',
  template: ''
})
export class AmortizationEntryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationEntry }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmortizationEntryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.amortizationEntry = amortizationEntry;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amortization-entry', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amortization-entry', { outlets: { popup: null } }]);
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
