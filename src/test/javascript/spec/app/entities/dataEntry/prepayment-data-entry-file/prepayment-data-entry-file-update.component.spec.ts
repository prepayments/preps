/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { PrepaymentDataEntryFileUpdateComponent } from 'app/entities/dataEntry/prepayment-data-entry-file/prepayment-data-entry-file-update.component';
import { PrepaymentDataEntryFileService } from 'app/entities/dataEntry/prepayment-data-entry-file/prepayment-data-entry-file.service';
import { PrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';

describe('Component Tests', () => {
  describe('PrepaymentDataEntryFile Management Update Component', () => {
    let comp: PrepaymentDataEntryFileUpdateComponent;
    let fixture: ComponentFixture<PrepaymentDataEntryFileUpdateComponent>;
    let service: PrepaymentDataEntryFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [PrepaymentDataEntryFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrepaymentDataEntryFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrepaymentDataEntryFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrepaymentDataEntryFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrepaymentDataEntryFile(123);
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
        const entity = new PrepaymentDataEntryFile();
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
