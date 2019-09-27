/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationEntryUpdateDetailComponent } from 'app/entities/updates/amortization-entry-update/amortization-entry-update-detail.component';
import { AmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';

describe('Component Tests', () => {
  describe('AmortizationEntryUpdate Management Detail Component', () => {
    let comp: AmortizationEntryUpdateDetailComponent;
    let fixture: ComponentFixture<AmortizationEntryUpdateDetailComponent>;
    const route = ({ data: of({ amortizationEntryUpdate: new AmortizationEntryUpdate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationEntryUpdateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AmortizationEntryUpdateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationEntryUpdateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amortizationEntryUpdate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
