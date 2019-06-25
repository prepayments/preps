import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';
import { AmortizationUploadFileService } from './amortization-upload-file.service';

@Component({
  selector: 'gha-amortization-upload-file-delete-dialog',
  templateUrl: './amortization-upload-file-delete-dialog.component.html'
})
export class AmortizationUploadFileDeleteDialogComponent {
  amortizationUploadFile: IAmortizationUploadFile;

  constructor(
    protected amortizationUploadFileService: AmortizationUploadFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amortizationUploadFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amortizationUploadFileListModification',
        content: 'Deleted an amortizationUploadFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-amortization-upload-file-delete-popup',
  template: ''
})
export class AmortizationUploadFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationUploadFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmortizationUploadFileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.amortizationUploadFile = amortizationUploadFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amortization-upload-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amortization-upload-file', { outlets: { popup: null } }]);
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
