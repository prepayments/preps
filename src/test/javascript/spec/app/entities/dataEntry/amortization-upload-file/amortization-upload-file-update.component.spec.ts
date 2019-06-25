/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUploadFileUpdateComponent } from 'app/entities/dataEntry/amortization-upload-file/amortization-upload-file-update.component';
import { AmortizationUploadFileService } from 'app/entities/dataEntry/amortization-upload-file/amortization-upload-file.service';
import { AmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';

describe('Component Tests', () => {
  describe('AmortizationUploadFile Management Update Component', () => {
    let comp: AmortizationUploadFileUpdateComponent;
    let fixture: ComponentFixture<AmortizationUploadFileUpdateComponent>;
    let service: AmortizationUploadFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUploadFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmortizationUploadFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationUploadFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationUploadFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationUploadFile(123);
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
        const entity = new AmortizationUploadFile();
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
