/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationEntryUpdateUpdateComponent } from 'app/entities/updates/amortization-entry-update/amortization-entry-update-update.component';
import { AmortizationEntryUpdateService } from 'app/entities/updates/amortization-entry-update/amortization-entry-update.service';
import { AmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';

describe('Component Tests', () => {
  describe('AmortizationEntryUpdate Management Update Component', () => {
    let comp: AmortizationEntryUpdateUpdateComponent;
    let fixture: ComponentFixture<AmortizationEntryUpdateUpdateComponent>;
    let service: AmortizationEntryUpdateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationEntryUpdateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmortizationEntryUpdateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationEntryUpdateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationEntryUpdateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationEntryUpdate(123);
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
        const entity = new AmortizationEntryUpdate();
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
