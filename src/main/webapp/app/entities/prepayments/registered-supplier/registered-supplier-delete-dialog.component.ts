import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { RegisteredSupplierService } from './registered-supplier.service';

@Component({
  selector: 'gha-registered-supplier-delete-dialog',
  templateUrl: './registered-supplier-delete-dialog.component.html'
})
export class RegisteredSupplierDeleteDialogComponent {
  registeredSupplier: IRegisteredSupplier;

  constructor(
    protected registeredSupplierService: RegisteredSupplierService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.registeredSupplierService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'registeredSupplierListModification',
        content: 'Deleted an registeredSupplier'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-registered-supplier-delete-popup',
  template: ''
})
export class RegisteredSupplierDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ registeredSupplier }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RegisteredSupplierDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.registeredSupplier = registeredSupplier;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/registered-supplier', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/registered-supplier', { outlets: { popup: null } }]);
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
