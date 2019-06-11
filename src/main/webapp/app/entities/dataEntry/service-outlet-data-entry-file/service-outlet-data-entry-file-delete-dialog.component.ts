import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';
import { ServiceOutletDataEntryFileService } from './service-outlet-data-entry-file.service';

@Component({
  selector: 'gha-service-outlet-data-entry-file-delete-dialog',
  templateUrl: './service-outlet-data-entry-file-delete-dialog.component.html'
})
export class ServiceOutletDataEntryFileDeleteDialogComponent {
  serviceOutletDataEntryFile: IServiceOutletDataEntryFile;

  constructor(
    protected serviceOutletDataEntryFileService: ServiceOutletDataEntryFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceOutletDataEntryFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceOutletDataEntryFileListModification',
        content: 'Deleted an serviceOutletDataEntryFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-service-outlet-data-entry-file-delete-popup',
  template: ''
})
export class ServiceOutletDataEntryFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOutletDataEntryFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceOutletDataEntryFileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.serviceOutletDataEntryFile = serviceOutletDataEntryFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-outlet-data-entry-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-outlet-data-entry-file', { outlets: { popup: null } }]);
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
