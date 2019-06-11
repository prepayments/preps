import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';

@Component({
  selector: 'gha-transaction-account-detail',
  templateUrl: './transaction-account-detail.component.html'
})
export class TransactionAccountDetailComponent implements OnInit {
  transactionAccount: ITransactionAccount;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionAccount }) => {
      this.transactionAccount = transactionAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
