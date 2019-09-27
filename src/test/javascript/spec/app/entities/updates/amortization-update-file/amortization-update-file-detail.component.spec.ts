/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUpdateFileDetailComponent } from 'app/entities/updates/amortization-update-file/amortization-update-file-detail.component';
import { AmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';

describe('Component Tests', () => {
  describe('AmortizationUpdateFile Management Detail Component', () => {
    let comp: AmortizationUpdateFileDetailComponent;
    let fixture: ComponentFixture<AmortizationUpdateFileDetailComponent>;
    const route = ({ data: of({ amortizationUpdateFile: new AmortizationUpdateFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUpdateFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AmortizationUpdateFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationUpdateFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amortizationUpdateFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
