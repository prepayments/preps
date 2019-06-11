/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { PrepaymentEntryDeleteDialogComponent } from 'app/entities/prepayments/prepayment-entry/prepayment-entry-delete-dialog.component';
import { PrepaymentEntryService } from 'app/entities/prepayments/prepayment-entry/prepayment-entry.service';

describe('Component Tests', () => {
  describe('PrepaymentEntry Management Delete Component', () => {
    let comp: PrepaymentEntryDeleteDialogComponent;
    let fixture: ComponentFixture<PrepaymentEntryDeleteDialogComponent>;
    let service: PrepaymentEntryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [PrepaymentEntryDeleteDialogComponent]
      })
        .overrideTemplate(PrepaymentEntryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrepaymentEntryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrepaymentEntryService);
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
