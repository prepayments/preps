/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUpdateFileUpdateComponent } from 'app/entities/updates/amortization-update-file/amortization-update-file-update.component';
import { AmortizationUpdateFileService } from 'app/entities/updates/amortization-update-file/amortization-update-file.service';
import { AmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';

describe('Component Tests', () => {
  describe('AmortizationUpdateFile Management Update Component', () => {
    let comp: AmortizationUpdateFileUpdateComponent;
    let fixture: ComponentFixture<AmortizationUpdateFileUpdateComponent>;
    let service: AmortizationUpdateFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUpdateFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmortizationUpdateFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationUpdateFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationUpdateFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationUpdateFile(123);
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
        const entity = new AmortizationUpdateFile();
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
