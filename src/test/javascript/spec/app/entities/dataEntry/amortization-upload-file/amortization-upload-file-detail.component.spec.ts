/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUploadFileDetailComponent } from 'app/entities/dataEntry/amortization-upload-file/amortization-upload-file-detail.component';
import { AmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';

describe('Component Tests', () => {
  describe('AmortizationUploadFile Management Detail Component', () => {
    let comp: AmortizationUploadFileDetailComponent;
    let fixture: ComponentFixture<AmortizationUploadFileDetailComponent>;
    const route = ({ data: of({ amortizationUploadFile: new AmortizationUploadFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUploadFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AmortizationUploadFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationUploadFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amortizationUploadFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
