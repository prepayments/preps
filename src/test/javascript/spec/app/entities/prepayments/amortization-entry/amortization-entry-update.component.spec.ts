/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationEntryUpdateComponent } from 'app/entities/prepayments/amortization-entry/amortization-entry-update.component';
import { AmortizationEntryService } from 'app/entities/prepayments/amortization-entry/amortization-entry.service';
import { AmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

describe('Component Tests', () => {
  describe('AmortizationEntry Management Update Component', () => {
    let comp: AmortizationEntryUpdateComponent;
    let fixture: ComponentFixture<AmortizationEntryUpdateComponent>;
    let service: AmortizationEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationEntryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmortizationEntryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationEntryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationEntryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationEntry(123);
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
        const entity = new AmortizationEntry();
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
