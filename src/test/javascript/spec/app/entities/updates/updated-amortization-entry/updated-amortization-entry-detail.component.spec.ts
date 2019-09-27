/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { UpdatedAmortizationEntryDetailComponent } from 'app/entities/updates/updated-amortization-entry/updated-amortization-entry-detail.component';
import { UpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';

describe('Component Tests', () => {
  describe('UpdatedAmortizationEntry Management Detail Component', () => {
    let comp: UpdatedAmortizationEntryDetailComponent;
    let fixture: ComponentFixture<UpdatedAmortizationEntryDetailComponent>;
    const route = ({ data: of({ updatedAmortizationEntry: new UpdatedAmortizationEntry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [UpdatedAmortizationEntryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UpdatedAmortizationEntryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UpdatedAmortizationEntryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.updatedAmortizationEntry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
