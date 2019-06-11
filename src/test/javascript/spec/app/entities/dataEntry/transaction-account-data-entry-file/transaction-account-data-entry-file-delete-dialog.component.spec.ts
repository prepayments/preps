/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { TransactionAccountDataEntryFileDeleteDialogComponent } from 'app/entities/dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file-delete-dialog.component';
import { TransactionAccountDataEntryFileService } from 'app/entities/dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file.service';

describe('Component Tests', () => {
  describe('TransactionAccountDataEntryFile Management Delete Component', () => {
    let comp: TransactionAccountDataEntryFileDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionAccountDataEntryFileDeleteDialogComponent>;
    let service: TransactionAccountDataEntryFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [TransactionAccountDataEntryFileDeleteDialogComponent]
      })
        .overrideTemplate(TransactionAccountDataEntryFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionAccountDataEntryFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionAccountDataEntryFileService);
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
