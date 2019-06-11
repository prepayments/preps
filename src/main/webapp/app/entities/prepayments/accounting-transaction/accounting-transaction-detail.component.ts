import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';

@Component({
  selector: 'gha-accounting-transaction-detail',
  templateUrl: './accounting-transaction-detail.component.html'
})
export class AccountingTransactionDetailComponent implements OnInit {
  accountingTransaction: IAccountingTransaction;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountingTransaction }) => {
      this.accountingTransaction = accountingTransaction;
    });
  }

  previousState() {
    window.history.back();
  }
}
