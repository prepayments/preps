/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { ReportTypeDetailComponent } from 'app/entities/reports/report-type/report-type-detail.component';
import { ReportType } from 'app/shared/model/reports/report-type.model';

describe('Component Tests', () => {
  describe('ReportType Management Detail Component', () => {
    let comp: ReportTypeDetailComponent;
    let fixture: ComponentFixture<ReportTypeDetailComponent>;
    const route = ({ data: of({ reportType: new ReportType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ReportTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReportTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reportType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
