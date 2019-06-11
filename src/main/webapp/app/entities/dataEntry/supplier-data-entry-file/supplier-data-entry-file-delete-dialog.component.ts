import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';
import { SupplierDataEntryFileService } from './supplier-data-entry-file.service';

@Component({
  selector: 'gha-supplier-data-entry-file-delete-dialog',
  templateUrl: './supplier-data-entry-file-delete-dialog.component.html'
})
export class SupplierDataEntryFileDeleteDialogComponent {
  supplierDataEntryFile: ISupplierDataEntryFile;

  constructor(
    protected supplierDataEntryFileService: SupplierDataEntryFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierDataEntryFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'supplierDataEntryFileListModification',
        content: 'Deleted an supplierDataEntryFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-supplier-data-entry-file-delete-popup',
  template: ''
})
export class SupplierDataEntryFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierDataEntryFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SupplierDataEntryFileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.supplierDataEntryFile = supplierDataEntryFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/supplier-data-entry-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/supplier-data-entry-file', { outlets: { popup: null } }]);
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
