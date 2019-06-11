/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { PrepaymentEntryDetailComponent } from 'app/entities/prepayments/prepayment-entry/prepayment-entry-detail.component';
import { PrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

describe('Component Tests', () => {
  describe('PrepaymentEntry Management Detail Component', () => {
    let comp: PrepaymentEntryDetailComponent;
    let fixture: ComponentFixture<PrepaymentEntryDetailComponent>;
    const route = ({ data: of({ prepaymentEntry: new PrepaymentEntry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [PrepaymentEntryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrepaymentEntryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrepaymentEntryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prepaymentEntry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
