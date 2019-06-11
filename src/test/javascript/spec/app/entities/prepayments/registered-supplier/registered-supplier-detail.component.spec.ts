/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { RegisteredSupplierDetailComponent } from 'app/entities/prepayments/registered-supplier/registered-supplier-detail.component';
import { RegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';

describe('Component Tests', () => {
  describe('RegisteredSupplier Management Detail Component', () => {
    let comp: RegisteredSupplierDetailComponent;
    let fixture: ComponentFixture<RegisteredSupplierDetailComponent>;
    const route = ({ data: of({ registeredSupplier: new RegisteredSupplier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [RegisteredSupplierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegisteredSupplierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegisteredSupplierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.registeredSupplier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
