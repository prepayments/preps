/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { PrepaymentEntryUpdateComponent } from 'app/entities/prepayments/prepayment-entry/prepayment-entry-update.component';
import { PrepaymentEntryService } from 'app/entities/prepayments/prepayment-entry/prepayment-entry.service';
import { PrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

describe('Component Tests', () => {
  describe('PrepaymentEntry Management Update Component', () => {
    let comp: PrepaymentEntryUpdateComponent;
    let fixture: ComponentFixture<PrepaymentEntryUpdateComponent>;
    let service: PrepaymentEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [PrepaymentEntryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrepaymentEntryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrepaymentEntryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrepaymentEntryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrepaymentEntry(123);
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
        const entity = new PrepaymentEntry();
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
