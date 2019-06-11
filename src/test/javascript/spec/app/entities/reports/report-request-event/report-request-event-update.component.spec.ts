/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { ReportRequestEventUpdateComponent } from 'app/entities/reports/report-request-event/report-request-event-update.component';
import { ReportRequestEventService } from 'app/entities/reports/report-request-event/report-request-event.service';
import { ReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';

describe('Component Tests', () => {
  describe('ReportRequestEvent Management Update Component', () => {
    let comp: ReportRequestEventUpdateComponent;
    let fixture: ComponentFixture<ReportRequestEventUpdateComponent>;
    let service: ReportRequestEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ReportRequestEventUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReportRequestEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportRequestEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportRequestEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportRequestEvent(123);
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
        const entity = new ReportRequestEvent();
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
