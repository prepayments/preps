/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { UpdatedAmortizationEntryUpdateComponent } from 'app/entities/updates/updated-amortization-entry/updated-amortization-entry-update.component';
import { UpdatedAmortizationEntryService } from 'app/entities/updates/updated-amortization-entry/updated-amortization-entry.service';
import { UpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';

describe('Component Tests', () => {
  describe('UpdatedAmortizationEntry Management Update Component', () => {
    let comp: UpdatedAmortizationEntryUpdateComponent;
    let fixture: ComponentFixture<UpdatedAmortizationEntryUpdateComponent>;
    let service: UpdatedAmortizationEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [UpdatedAmortizationEntryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UpdatedAmortizationEntryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UpdatedAmortizationEntryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UpdatedAmortizationEntryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UpdatedAmortizationEntry(123);
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
        const entity = new UpdatedAmortizationEntry();
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
