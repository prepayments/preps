/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { PrepaymentDataEntryFileDetailComponent } from 'app/entities/dataEntry/prepayment-data-entry-file/prepayment-data-entry-file-detail.component';
import { PrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';

describe('Component Tests', () => {
  describe('PrepaymentDataEntryFile Management Detail Component', () => {
    let comp: PrepaymentDataEntryFileDetailComponent;
    let fixture: ComponentFixture<PrepaymentDataEntryFileDetailComponent>;
    const route = ({ data: of({ prepaymentDataEntryFile: new PrepaymentDataEntryFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [PrepaymentDataEntryFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrepaymentDataEntryFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrepaymentDataEntryFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prepaymentDataEntryFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
