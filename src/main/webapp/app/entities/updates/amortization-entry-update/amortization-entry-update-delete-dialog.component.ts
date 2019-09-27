import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';
import { AmortizationEntryUpdateService } from './amortization-entry-update.service';

@Component({
  selector: 'gha-amortization-entry-update-delete-dialog',
  templateUrl: './amortization-entry-update-delete-dialog.component.html'
})
export class AmortizationEntryUpdateDeleteDialogComponent {
  amortizationEntryUpdate: IAmortizationEntryUpdate;

  constructor(
    protected amortizationEntryUpdateService: AmortizationEntryUpdateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amortizationEntryUpdateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amortizationEntryUpdateListModification',
        content: 'Deleted an amortizationEntryUpdate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-amortization-entry-update-delete-popup',
  template: ''
})
export class AmortizationEntryUpdateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationEntryUpdate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmortizationEntryUpdateDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.amortizationEntryUpdate = amortizationEntryUpdate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amortization-entry-update', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amortization-entry-update', { outlets: { popup: null } }]);
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
