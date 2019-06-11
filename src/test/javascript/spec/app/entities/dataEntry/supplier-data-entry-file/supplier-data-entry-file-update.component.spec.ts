/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { SupplierDataEntryFileUpdateComponent } from 'app/entities/dataEntry/supplier-data-entry-file/supplier-data-entry-file-update.component';
import { SupplierDataEntryFileService } from 'app/entities/dataEntry/supplier-data-entry-file/supplier-data-entry-file.service';
import { SupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';

describe('Component Tests', () => {
  describe('SupplierDataEntryFile Management Update Component', () => {
    let comp: SupplierDataEntryFileUpdateComponent;
    let fixture: ComponentFixture<SupplierDataEntryFileUpdateComponent>;
    let service: SupplierDataEntryFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [SupplierDataEntryFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupplierDataEntryFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierDataEntryFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierDataEntryFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupplierDataEntryFile(123);
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
        const entity = new SupplierDataEntryFile();
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
