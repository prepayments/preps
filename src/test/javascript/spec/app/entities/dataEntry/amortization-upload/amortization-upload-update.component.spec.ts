/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUploadUpdateComponent } from 'app/entities/dataEntry/amortization-upload/amortization-upload-update.component';
import { AmortizationUploadService } from 'app/entities/dataEntry/amortization-upload/amortization-upload.service';
import { AmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';

describe('Component Tests', () => {
  describe('AmortizationUpload Management Update Component', () => {
    let comp: AmortizationUploadUpdateComponent;
    let fixture: ComponentFixture<AmortizationUploadUpdateComponent>;
    let service: AmortizationUploadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUploadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmortizationUploadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationUploadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationUploadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationUpload(123);
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
        const entity = new AmortizationUpload();
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
