/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { AccountingTransactionDeleteDialogComponent } from 'app/entities/prepayments/accounting-transaction/accounting-transaction-delete-dialog.component';
import { AccountingTransactionService } from 'app/entities/prepayments/accounting-transaction/accounting-transaction.service';

describe('Component Tests', () => {
  describe('AccountingTransaction Management Delete Component', () => {
    let comp: AccountingTransactionDeleteDialogComponent;
    let fixture: ComponentFixture<AccountingTransactionDeleteDialogComponent>;
    let service: AccountingTransactionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AccountingTransactionDeleteDialogComponent]
      })
        .overrideTemplate(AccountingTransactionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountingTransactionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountingTransactionService);
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
