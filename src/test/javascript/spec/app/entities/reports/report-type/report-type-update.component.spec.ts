/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { ReportTypeUpdateComponent } from 'app/entities/reports/report-type/report-type-update.component';
import { ReportTypeService } from 'app/entities/reports/report-type/report-type.service';
import { ReportType } from 'app/shared/model/reports/report-type.model';

describe('Component Tests', () => {
  describe('ReportType Management Update Component', () => {
    let comp: ReportTypeUpdateComponent;
    let fixture: ComponentFixture<ReportTypeUpdateComponent>;
    let service: ReportTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ReportTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReportTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportType(123);
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
        const entity = new ReportType();
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
