import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportType } from 'app/shared/model/reports/report-type.model';
import { ReportTypeService } from './report-type.service';

@Component({
  selector: 'gha-report-type-delete-dialog',
  templateUrl: './report-type-delete-dialog.component.html'
})
export class ReportTypeDeleteDialogComponent {
  reportType: IReportType;

  constructor(
    protected reportTypeService: ReportTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reportTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reportTypeListModification',
        content: 'Deleted an reportType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-report-type-delete-popup',
  template: ''
})
export class ReportTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReportTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reportType = reportType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/report-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/report-type', { outlets: { popup: null } }]);
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
