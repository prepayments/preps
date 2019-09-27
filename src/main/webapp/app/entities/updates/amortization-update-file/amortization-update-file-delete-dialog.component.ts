import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';
import { AmortizationUpdateFileService } from './amortization-update-file.service';

@Component({
  selector: 'gha-amortization-update-file-delete-dialog',
  templateUrl: './amortization-update-file-delete-dialog.component.html'
})
export class AmortizationUpdateFileDeleteDialogComponent {
  amortizationUpdateFile: IAmortizationUpdateFile;

  constructor(
    protected amortizationUpdateFileService: AmortizationUpdateFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amortizationUpdateFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amortizationUpdateFileListModification',
        content: 'Deleted an amortizationUpdateFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-amortization-update-file-delete-popup',
  template: ''
})
export class AmortizationUpdateFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationUpdateFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmortizationUpdateFileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.amortizationUpdateFile = amortizationUpdateFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amortization-update-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amortization-update-file', { outlets: { popup: null } }]);
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
