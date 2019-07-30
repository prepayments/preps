import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IAccountingTransaction, AccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';
import { AccountingTransactionService } from './accounting-transaction.service';

@Component({
  selector: 'gha-accounting-transaction-update',
  templateUrl: './accounting-transaction-update.component.html'
})
export class AccountingTransactionUpdateComponent implements OnInit {
  accountingTransaction: IAccountingTransaction;
  isSaving: boolean;
  transactionDateDp: any;

  editForm = this.fb.group({
    id: [],
    description: [],
    serviceOutletCode: [null, [Validators.required, Validators.pattern('^[0-9]{3}$')]],
    accountName: [null, [Validators.required]],
    accountNumber: [null, [Validators.required, Validators.pattern('^[0-9]{10,16}$')]],
    transactionDate: [null, [Validators.required]],
    transactionAmount: [null, [Validators.required]],
    incrementAccount: [null, [Validators.required]]
  });

  constructor(
    protected accountingTransactionService: AccountingTransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountingTransaction }) => {
      this.updateForm(accountingTransaction);
      this.accountingTransaction = accountingTransaction;
    });
  }

  updateForm(accountingTransaction: IAccountingTransaction) {
    this.editForm.patchValue({
      id: accountingTransaction.id,
      description: accountingTransaction.description,
      serviceOutletCode: accountingTransaction.serviceOutletCode,
      accountName: accountingTransaction.accountName,
      accountNumber: accountingTransaction.accountNumber,
      transactionDate: accountingTransaction.transactionDate,
      transactionAmount: accountingTransaction.transactionAmount,
      incrementAccount: accountingTransaction.incrementAccount
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountingTransaction = this.createFromForm();
    if (accountingTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.accountingTransactionService.update(accountingTransaction));
    } else {
      this.subscribeToSaveResponse(this.accountingTransactionService.create(accountingTransaction));
    }
  }

  private createFromForm(): IAccountingTransaction {
    const entity = {
      ...new AccountingTransaction(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      serviceOutletCode: this.editForm.get(['serviceOutletCode']).value,
      accountName: this.editForm.get(['accountName']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      transactionDate: this.editForm.get(['transactionDate']).value,
      transactionAmount: this.editForm.get(['transactionAmount']).value,
      incrementAccount: this.editForm.get(['incrementAccount']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountingTransaction>>) {
    result.subscribe((res: HttpResponse<IAccountingTransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
