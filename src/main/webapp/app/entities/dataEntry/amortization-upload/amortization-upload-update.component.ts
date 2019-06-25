import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IAmortizationUpload, AmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';
import { AmortizationUploadService } from './amortization-upload.service';

@Component({
  selector: 'gha-amortization-upload-update',
  templateUrl: './amortization-upload-update.component.html'
})
export class AmortizationUploadUpdateComponent implements OnInit {
  amortizationUpload: IAmortizationUpload;
  isSaving: boolean;
  prepaymentTransactionDateDp: any;
  firstAmortizationDateDp: any;

  editForm = this.fb.group({
    id: [],
    accountName: [null, [Validators.required]],
    particulars: [null, [Validators.required]],
    serviceOutletCode: [null, [Validators.required]],
    prepaymentAccountNumber: [null, [Validators.required]],
    expenseAccountNumber: [null, [Validators.required]],
    prepaymentTransactionId: [null, [Validators.required]],
    prepaymentTransactionDate: [null, [Validators.required]],
    prepaymentTransactionAmount: [],
    amortizationAmount: [null, [Validators.required]],
    numberOfAmortizations: [null, [Validators.required, Validators.min(1)]],
    firstAmortizationDate: [null, [Validators.required]]
  });

  constructor(
    protected amortizationUploadService: AmortizationUploadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amortizationUpload }) => {
      this.updateForm(amortizationUpload);
      this.amortizationUpload = amortizationUpload;
    });
  }

  updateForm(amortizationUpload: IAmortizationUpload) {
    this.editForm.patchValue({
      id: amortizationUpload.id,
      accountName: amortizationUpload.accountName,
      particulars: amortizationUpload.particulars,
      serviceOutletCode: amortizationUpload.serviceOutletCode,
      prepaymentAccountNumber: amortizationUpload.prepaymentAccountNumber,
      expenseAccountNumber: amortizationUpload.expenseAccountNumber,
      prepaymentTransactionId: amortizationUpload.prepaymentTransactionId,
      prepaymentTransactionDate: amortizationUpload.prepaymentTransactionDate,
      prepaymentTransactionAmount: amortizationUpload.prepaymentTransactionAmount,
      amortizationAmount: amortizationUpload.amortizationAmount,
      numberOfAmortizations: amortizationUpload.numberOfAmortizations,
      firstAmortizationDate: amortizationUpload.firstAmortizationDate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const amortizationUpload = this.createFromForm();
    if (amortizationUpload.id !== undefined) {
      this.subscribeToSaveResponse(this.amortizationUploadService.update(amortizationUpload));
    } else {
      this.subscribeToSaveResponse(this.amortizationUploadService.create(amortizationUpload));
    }
  }

  private createFromForm(): IAmortizationUpload {
    const entity = {
      ...new AmortizationUpload(),
      id: this.editForm.get(['id']).value,
      accountName: this.editForm.get(['accountName']).value,
      particulars: this.editForm.get(['particulars']).value,
      serviceOutletCode: this.editForm.get(['serviceOutletCode']).value,
      prepaymentAccountNumber: this.editForm.get(['prepaymentAccountNumber']).value,
      expenseAccountNumber: this.editForm.get(['expenseAccountNumber']).value,
      prepaymentTransactionId: this.editForm.get(['prepaymentTransactionId']).value,
      prepaymentTransactionDate: this.editForm.get(['prepaymentTransactionDate']).value,
      prepaymentTransactionAmount: this.editForm.get(['prepaymentTransactionAmount']).value,
      amortizationAmount: this.editForm.get(['amortizationAmount']).value,
      numberOfAmortizations: this.editForm.get(['numberOfAmortizations']).value,
      firstAmortizationDate: this.editForm.get(['firstAmortizationDate']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationUpload>>) {
    result.subscribe((res: HttpResponse<IAmortizationUpload>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
