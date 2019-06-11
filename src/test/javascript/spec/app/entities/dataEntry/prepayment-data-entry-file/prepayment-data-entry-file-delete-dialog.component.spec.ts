/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { PrepaymentDataEntryFileDeleteDialogComponent } from 'app/entities/dataEntry/prepayment-data-entry-file/prepayment-data-entry-file-delete-dialog.component';
import { PrepaymentDataEntryFileService } from 'app/entities/dataEntry/prepayment-data-entry-file/prepayment-data-entry-file.service';

describe('Component Tests', () => {
  describe('PrepaymentDataEntryFile Management Delete Component', () => {
    let comp: PrepaymentDataEntryFileDeleteDialogComponent;
    let fixture: ComponentFixture<PrepaymentDataEntryFileDeleteDialogComponent>;
    let service: PrepaymentDataEntryFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [PrepaymentDataEntryFileDeleteDialogComponent]
      })
        .overrideTemplate(PrepaymentDataEntryFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrepaymentDataEntryFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrepaymentDataEntryFileService);
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
