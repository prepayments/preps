import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';
import { AmortizationDataEntryFileService } from './amortization-data-entry-file.service';

@Component({
  selector: 'gha-amortization-data-entry-file-delete-dialog',
  templateUrl: './amortization-data-entry-file-delete-dialog.component.html'
})
export class AmortizationDataEntryFileDeleteDialogComponent {
  amortizationDataEntryFile: IAmortizationDataEntryFile;

  constructor(
    protected amortizationDataEntryFileService: AmortizationDataEntryFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amortizationDataEntryFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amortizationDataEntryFileListModification',
        content: 'Deleted an amortizationDataEntryFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-amortization-data-entry-file-delete-popup',
  template: ''
})
export class AmortizationDataEntryFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationDataEntryFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmortizationDataEntryFileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.amortizationDataEntryFile = amortizationDataEntryFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amortization-data-entry-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amortization-data-entry-file', { outlets: { popup: null } }]);
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
