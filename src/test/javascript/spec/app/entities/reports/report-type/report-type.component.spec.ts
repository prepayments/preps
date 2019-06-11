/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PrepsTestModule } from '../../../../test.module';
import { ReportTypeComponent } from 'app/entities/reports/report-type/report-type.component';
import { ReportTypeService } from 'app/entities/reports/report-type/report-type.service';
import { ReportType } from 'app/shared/model/reports/report-type.model';

describe('Component Tests', () => {
  describe('ReportType Management Component', () => {
    let comp: ReportTypeComponent;
    let fixture: ComponentFixture<ReportTypeComponent>;
    let service: ReportTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ReportTypeComponent],
        providers: []
      })
        .overrideTemplate(ReportTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReportType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reportTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
