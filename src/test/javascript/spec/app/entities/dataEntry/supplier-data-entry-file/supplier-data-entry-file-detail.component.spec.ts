/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { SupplierDataEntryFileDetailComponent } from 'app/entities/dataEntry/supplier-data-entry-file/supplier-data-entry-file-detail.component';
import { SupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';

describe('Component Tests', () => {
  describe('SupplierDataEntryFile Management Detail Component', () => {
    let comp: SupplierDataEntryFileDetailComponent;
    let fixture: ComponentFixture<SupplierDataEntryFileDetailComponent>;
    const route = ({ data: of({ supplierDataEntryFile: new SupplierDataEntryFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [SupplierDataEntryFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupplierDataEntryFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierDataEntryFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierDataEntryFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
