/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { TransactionAccountUpdateComponent } from 'app/entities/prepayments/transaction-account/transaction-account-update.component';
import { TransactionAccountService } from 'app/entities/prepayments/transaction-account/transaction-account.service';
import { TransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';

describe('Component Tests', () => {
  describe('TransactionAccount Management Update Component', () => {
    let comp: TransactionAccountUpdateComponent;
    let fixture: ComponentFixture<TransactionAccountUpdateComponent>;
    let service: TransactionAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [TransactionAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionAccount(123);
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
        const entity = new TransactionAccount();
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
