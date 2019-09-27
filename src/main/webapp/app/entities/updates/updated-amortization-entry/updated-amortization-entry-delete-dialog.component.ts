import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';
import { UpdatedAmortizationEntryService } from './updated-amortization-entry.service';

@Component({
  selector: 'gha-updated-amortization-entry-delete-dialog',
  templateUrl: './updated-amortization-entry-delete-dialog.component.html'
})
export class UpdatedAmortizationEntryDeleteDialogComponent {
  updatedAmortizationEntry: IUpdatedAmortizationEntry;

  constructor(
    protected updatedAmortizationEntryService: UpdatedAmortizationEntryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.updatedAmortizationEntryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'updatedAmortizationEntryListModification',
        content: 'Deleted an updatedAmortizationEntry'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-updated-amortization-entry-delete-popup',
  template: ''
})
export class UpdatedAmortizationEntryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ updatedAmortizationEntry }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UpdatedAmortizationEntryDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.updatedAmortizationEntry = updatedAmortizationEntry;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/updated-amortization-entry', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/updated-amortization-entry', { outlets: { popup: null } }]);
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
