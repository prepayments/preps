/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { ReportRequestEventDetailComponent } from 'app/entities/reports/report-request-event/report-request-event-detail.component';
import { ReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';

describe('Component Tests', () => {
  describe('ReportRequestEvent Management Detail Component', () => {
    let comp: ReportRequestEventDetailComponent;
    let fixture: ComponentFixture<ReportRequestEventDetailComponent>;
    const route = ({ data: of({ reportRequestEvent: new ReportRequestEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ReportRequestEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReportRequestEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportRequestEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reportRequestEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
