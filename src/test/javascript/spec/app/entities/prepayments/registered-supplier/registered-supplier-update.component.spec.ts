/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { RegisteredSupplierUpdateComponent } from 'app/entities/prepayments/registered-supplier/registered-supplier-update.component';
import { RegisteredSupplierService } from 'app/entities/prepayments/registered-supplier/registered-supplier.service';
import { RegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';

describe('Component Tests', () => {
  describe('RegisteredSupplier Management Update Component', () => {
    let comp: RegisteredSupplierUpdateComponent;
    let fixture: ComponentFixture<RegisteredSupplierUpdateComponent>;
    let service: RegisteredSupplierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [RegisteredSupplierUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegisteredSupplierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegisteredSupplierUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegisteredSupplierService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegisteredSupplier(123);
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
        const entity = new RegisteredSupplier();
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
