/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AccountingTransactionUpdateComponent } from 'app/entities/prepayments/accounting-transaction/accounting-transaction-update.component';
import { AccountingTransactionService } from 'app/entities/prepayments/accounting-transaction/accounting-transaction.service';
import { AccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';

describe('Component Tests', () => {
  describe('AccountingTransaction Management Update Component', () => {
    let comp: AccountingTransactionUpdateComponent;
    let fixture: ComponentFixture<AccountingTransactionUpdateComponent>;
    let service: AccountingTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AccountingTransactionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountingTransactionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountingTransactionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountingTransactionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountingTransaction(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountingTransaction();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
