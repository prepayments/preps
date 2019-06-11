/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { ServiceOutletDataEntryFileUpdateComponent } from 'app/entities/dataEntry/service-outlet-data-entry-file/service-outlet-data-entry-file-update.component';
import { ServiceOutletDataEntryFileService } from 'app/entities/dataEntry/service-outlet-data-entry-file/service-outlet-data-entry-file.service';
import { ServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';

describe('Component Tests', () => {
  describe('ServiceOutletDataEntryFile Management Update Component', () => {
    let comp: ServiceOutletDataEntryFileUpdateComponent;
    let fixture: ComponentFixture<ServiceOutletDataEntryFileUpdateComponent>;
    let service: ServiceOutletDataEntryFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ServiceOutletDataEntryFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceOutletDataEntryFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceOutletDataEntryFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOutletDataEntryFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceOutletDataEntryFile(123);
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
        const entity = new ServiceOutletDataEntryFile();
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
