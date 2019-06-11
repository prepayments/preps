/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { ReportRequestEventDeleteDialogComponent } from 'app/entities/reports/report-request-event/report-request-event-delete-dialog.component';
import { ReportRequestEventService } from 'app/entities/reports/report-request-event/report-request-event.service';

describe('Component Tests', () => {
  describe('ReportRequestEvent Management Delete Component', () => {
    let comp: ReportRequestEventDeleteDialogComponent;
    let fixture: ComponentFixture<ReportRequestEventDeleteDialogComponent>;
    let service: ReportRequestEventService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ReportRequestEventDeleteDialogComponent]
      })
        .overrideTemplate(ReportRequestEventDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportRequestEventDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportRequestEventService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
