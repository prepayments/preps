/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { TransactionAccountDataEntryFileUpdateComponent } from 'app/entities/dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file-update.component';
import { TransactionAccountDataEntryFileService } from 'app/entities/dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file.service';
import { TransactionAccountDataEntryFile } from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';

describe('Component Tests', () => {
  describe('TransactionAccountDataEntryFile Management Update Component', () => {
    let comp: TransactionAccountDataEntryFileUpdateComponent;
    let fixture: ComponentFixture<TransactionAccountDataEntryFileUpdateComponent>;
    let service: TransactionAccountDataEntryFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [TransactionAccountDataEntryFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionAccountDataEntryFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionAccountDataEntryFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionAccountDataEntryFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionAccountDataEntryFile(123);
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
        const entity = new TransactionAccountDataEntryFile();
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
