import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';
import { PrepaymentEntryService } from './prepayment-entry.service';

@Component({
  selector: 'gha-prepayment-entry-delete-dialog',
  templateUrl: './prepayment-entry-delete-dialog.component.html'
})
export class PrepaymentEntryDeleteDialogComponent {
  prepaymentEntry: IPrepaymentEntry;

  constructor(
    protected prepaymentEntryService: PrepaymentEntryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.prepaymentEntryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'prepaymentEntryListModification',
        content: 'Deleted an prepaymentEntry'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-prepayment-entry-delete-popup',
  template: ''
})
export class PrepaymentEntryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prepaymentEntry }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PrepaymentEntryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.prepaymentEntry = prepaymentEntry;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/prepayment-entry', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/prepayment-entry', { outlets: { popup: null } }]);
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
