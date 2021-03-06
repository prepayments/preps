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
    amortizationServiceOutletCode: [null, [Validators.required]],
    prepaymentServiceOutletCode: [null, [Validators.required]],
    prepaymentAccountNumber: [null, [Validators.required]],
    expenseAccountNumber: [null, [Validators.required]],
    prepaymentTransactionId: [null, [Validators.required]],
    prepaymentTransactionDate: [null, [Validators.required]],
    prepaymentTransactionAmount: [],
    amortizationAmount: [null, [Validators.required]],
    numberOfAmortizations: [null, [Validators.required, Validators.min(1)]],
    firstAmortizationDate: [null, [Validators.required]],
    monthlyAmortizationDate: [null, [Validators.min(1), Validators.max(28)]],
    uploadSuccessful: [],
    uploadOrphaned: [],
    originatingFileToken: [],
    amortizationTag: []
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
      amortizationServiceOutletCode: amortizationUpload.amortizationServiceOutletCode,
      prepaymentServiceOutletCode: amortizationUpload.prepaymentServiceOutletCode,
      prepaymentAccountNumber: amortizationUpload.prepaymentAccountNumber,
      expenseAccountNumber: amortizationUpload.expenseAccountNumber,
      prepaymentTransactionId: amortizationUpload.prepaymentTransactionId,
      prepaymentTransactionDate: amortizationUpload.prepaymentTransactionDate,
      prepaymentTransactionAmount: amortizationUpload.prepaymentTransactionAmount,
      amortizationAmount: amortizationUpload.amortizationAmount,
      numberOfAmortizations: amortizationUpload.numberOfAmortizations,
      firstAmortizationDate: amortizationUpload.firstAmortizationDate,
      monthlyAmortizationDate: amortizationUpload.monthlyAmortizationDate,
      uploadSuccessful: amortizationUpload.uploadSuccessful,
      uploadOrphaned: amortizationUpload.uploadOrphaned,
      originatingFileToken: amortizationUpload.originatingFileToken,
      amortizationTag: amortizationUpload.amortizationTag
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
      amortizationServiceOutletCode: this.editForm.get(['amortizationServiceOutletCode']).value,
      prepaymentServiceOutletCode: this.editForm.get(['prepaymentServiceOutletCode']).value,
      prepaymentAccountNumber: this.editForm.get(['prepaymentAccountNumber']).value,
      expenseAccountNumber: this.editForm.get(['expenseAccountNumber']).value,
      prepaymentTransactionId: this.editForm.get(['prepaymentTransactionId']).value,
      prepaymentTransactionDate: this.editForm.get(['prepaymentTransactionDate']).value,
      prepaymentTransactionAmount: this.editForm.get(['prepaymentTransactionAmount']).value,
      amortizationAmount: this.editForm.get(['amortizationAmount']).value,
      numberOfAmortizations: this.editForm.get(['numberOfAmortizations']).value,
      firstAmortizationDate: this.editForm.get(['firstAmortizationDate']).value,
      monthlyAmortizationDate: this.editForm.get(['monthlyAmortizationDate']).value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful']).value,
      uploadOrphaned: this.editForm.get(['uploadOrphaned']).value,
      originatingFileToken: this.editForm.get(['originatingFileToken']).value,
      amortizationTag: this.editForm.get(['amortizationTag']).value
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
