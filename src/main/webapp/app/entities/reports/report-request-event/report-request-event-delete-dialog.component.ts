import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';
import { ReportRequestEventService } from './report-request-event.service';

@Component({
  selector: 'gha-report-request-event-delete-dialog',
  templateUrl: './report-request-event-delete-dialog.component.html'
})
export class ReportRequestEventDeleteDialogComponent {
  reportRequestEvent: IReportRequestEvent;

  constructor(
    protected reportRequestEventService: ReportRequestEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reportRequestEventService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reportRequestEventListModification',
        content: 'Deleted an reportRequestEvent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-report-request-event-delete-popup',
  template: ''
})
export class ReportRequestEventDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportRequestEvent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReportRequestEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reportRequestEvent = reportRequestEvent;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/report-request-event', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/report-request-event', { outlets: { popup: null } }]);
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
