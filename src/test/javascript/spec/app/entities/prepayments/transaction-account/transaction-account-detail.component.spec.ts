/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { TransactionAccountDetailComponent } from 'app/entities/prepayments/transaction-account/transaction-account-detail.component';
import { TransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';

describe('Component Tests', () => {
  describe('TransactionAccount Management Detail Component', () => {
    let comp: TransactionAccountDetailComponent;
    let fixture: ComponentFixture<TransactionAccountDetailComponent>;
    const route = ({ data: of({ transactionAccount: new TransactionAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [TransactionAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
