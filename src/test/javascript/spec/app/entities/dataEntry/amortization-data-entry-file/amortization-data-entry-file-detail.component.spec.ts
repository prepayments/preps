/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationDataEntryFileDetailComponent } from 'app/entities/dataEntry/amortization-data-entry-file/amortization-data-entry-file-detail.component';
import { AmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';

describe('Component Tests', () => {
  describe('AmortizationDataEntryFile Management Detail Component', () => {
    let comp: AmortizationDataEntryFileDetailComponent;
    let fixture: ComponentFixture<AmortizationDataEntryFileDetailComponent>;
    const route = ({ data: of({ amortizationDataEntryFile: new AmortizationDataEntryFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationDataEntryFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AmortizationDataEntryFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationDataEntryFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amortizationDataEntryFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
