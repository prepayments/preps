/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationDataEntryFileUpdateComponent } from 'app/entities/dataEntry/amortization-data-entry-file/amortization-data-entry-file-update.component';
import { AmortizationDataEntryFileService } from 'app/entities/dataEntry/amortization-data-entry-file/amortization-data-entry-file.service';
import { AmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';

describe('Component Tests', () => {
  describe('AmortizationDataEntryFile Management Update Component', () => {
    let comp: AmortizationDataEntryFileUpdateComponent;
    let fixture: ComponentFixture<AmortizationDataEntryFileUpdateComponent>;
    let service: AmortizationDataEntryFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationDataEntryFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmortizationDataEntryFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationDataEntryFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationDataEntryFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationDataEntryFile(123);
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
        const entity = new AmortizationDataEntryFile();
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
