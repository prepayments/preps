import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';
import { PrepaymentDataEntryFileService } from './prepayment-data-entry-file.service';

@Component({
  selector: 'gha-prepayment-data-entry-file-delete-dialog',
  templateUrl: './prepayment-data-entry-file-delete-dialog.component.html'
})
export class PrepaymentDataEntryFileDeleteDialogComponent {
  prepaymentDataEntryFile: IPrepaymentDataEntryFile;

  constructor(
    protected prepaymentDataEntryFileService: PrepaymentDataEntryFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.prepaymentDataEntryFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'prepaymentDataEntryFileListModification',
        content: 'Deleted an prepaymentDataEntryFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-prepayment-data-entry-file-delete-popup',
  template: ''
})
export class PrepaymentDataEntryFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prepaymentDataEntryFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PrepaymentDataEntryFileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.prepaymentDataEntryFile = prepaymentDataEntryFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/prepayment-data-entry-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/prepayment-data-entry-file', { outlets: { popup: null } }]);
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
