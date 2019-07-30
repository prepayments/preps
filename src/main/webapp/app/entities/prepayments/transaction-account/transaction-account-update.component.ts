import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ITransactionAccount, TransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';
import { TransactionAccountService } from './transaction-account.service';

@Component({
  selector: 'gha-transaction-account-update',
  templateUrl: './transaction-account-update.component.html'
})
export class TransactionAccountUpdateComponent implements OnInit {
  transactionAccount: ITransactionAccount;
  isSaving: boolean;
  openingDateDp: any;

  editForm = this.fb.group({
    id: [],
    accountName: [null, [Validators.required]],
    accountNumber: [null, [Validators.required]],
    accountType: [],
    openingDate: [],
    originatingFileToken: []
  });

  constructor(
    protected transactionAccountService: TransactionAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionAccount }) => {
      this.updateForm(transactionAccount);
      this.transactionAccount = transactionAccount;
    });
  }

  updateForm(transactionAccount: ITransactionAccount) {
    this.editForm.patchValue({
      id: transactionAccount.id,
      accountName: transactionAccount.accountName,
      accountNumber: transactionAccount.accountNumber,
      accountType: transactionAccount.accountType,
      openingDate: transactionAccount.openingDate,
      originatingFileToken: transactionAccount.originatingFileToken
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionAccount = this.createFromForm();
    if (transactionAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionAccountService.update(transactionAccount));
    } else {
      this.subscribeToSaveResponse(this.transactionAccountService.create(transactionAccount));
    }
  }

  private createFromForm(): ITransactionAccount {
    const entity = {
      ...new TransactionAccount(),
      id: this.editForm.get(['id']).value,
      accountName: this.editForm.get(['accountName']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      accountType: this.editForm.get(['accountType']).value,
      openingDate: this.editForm.get(['openingDate']).value,
      originatingFileToken: this.editForm.get(['originatingFileToken']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionAccount>>) {
    result.subscribe((res: HttpResponse<ITransactionAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
