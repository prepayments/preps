import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';
import { AmortizationUploadService } from './amortization-upload.service';

@Component({
  selector: 'gha-amortization-upload-delete-dialog',
  templateUrl: './amortization-upload-delete-dialog.component.html'
})
export class AmortizationUploadDeleteDialogComponent {
  amortizationUpload: IAmortizationUpload;

  constructor(
    protected amortizationUploadService: AmortizationUploadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amortizationUploadService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amortizationUploadListModification',
        content: 'Deleted an amortizationUpload'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-amortization-upload-delete-popup',
  template: ''
})
export class AmortizationUploadDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationUpload }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmortizationUploadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.amortizationUpload = amortizationUpload;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amortization-upload', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amortization-upload', { outlets: { popup: null } }]);
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
