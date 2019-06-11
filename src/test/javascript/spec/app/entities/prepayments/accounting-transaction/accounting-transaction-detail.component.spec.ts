/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { AccountingTransactionDetailComponent } from 'app/entities/prepayments/accounting-transaction/accounting-transaction-detail.component';
import { AccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';

describe('Component Tests', () => {
  describe('AccountingTransaction Management Detail Component', () => {
    let comp: AccountingTransactionDetailComponent;
    let fixture: ComponentFixture<AccountingTransactionDetailComponent>;
    const route = ({ data: of({ accountingTransaction: new AccountingTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AccountingTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountingTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountingTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountingTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
