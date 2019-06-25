/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUploadDetailComponent } from 'app/entities/dataEntry/amortization-upload/amortization-upload-detail.component';
import { AmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';

describe('Component Tests', () => {
  describe('AmortizationUpload Management Detail Component', () => {
    let comp: AmortizationUploadDetailComponent;
    let fixture: ComponentFixture<AmortizationUploadDetailComponent>;
    const route = ({ data: of({ amortizationUpload: new AmortizationUpload(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUploadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AmortizationUploadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationUploadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amortizationUpload).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
