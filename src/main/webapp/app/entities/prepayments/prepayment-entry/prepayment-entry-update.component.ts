import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPrepaymentEntry, PrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';
import { PrepaymentEntryService } from './prepayment-entry.service';

@Component({
  selector: 'gha-prepayment-entry-update',
  templateUrl: './prepayment-entry-update.component.html'
})
export class PrepaymentEntryUpdateComponent implements OnInit {
  prepaymentEntry: IPrepaymentEntry;
  isSaving: boolean;
  prepaymentDateDp: any;

  editForm = this.fb.group({
    id: [],
    accountNumber: [null, [Validators.required, Validators.pattern('^[0-9]{5,16}$')]],
    accountName: [null, [Validators.required]],
    prepaymentId: [null, [Validators.required]],
    prepaymentDate: [null, [Validators.required]],
    particulars: [],
    serviceOutlet: [null, [Validators.required, Validators.pattern('^[0-9]{3}$')]],
    prepaymentAmount: [null, [Validators.required]],
    months: [],
    supplierName: [],
    invoiceNumber: [],
    scannedDocumentId: [],
    OriginatingFileToken: []
  });

  constructor(
    protected prepaymentEntryService: PrepaymentEntryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prepaymentEntry }) => {
      this.updateForm(prepaymentEntry);
      this.prepaymentEntry = prepaymentEntry;
    });
  }

  updateForm(prepaymentEntry: IPrepaymentEntry) {
    this.editForm.patchValue({
      id: prepaymentEntry.id,
      accountNumber: prepaymentEntry.accountNumber,
      accountName: prepaymentEntry.accountName,
      prepaymentId: prepaymentEntry.prepaymentId,
      prepaymentDate: prepaymentEntry.prepaymentDate,
      particulars: prepaymentEntry.particulars,
      serviceOutlet: prepaymentEntry.serviceOutlet,
      prepaymentAmount: prepaymentEntry.prepaymentAmount,
      months: prepaymentEntry.months,
      supplierName: prepaymentEntry.supplierName,
      invoiceNumber: prepaymentEntry.invoiceNumber,
      scannedDocumentId: prepaymentEntry.scannedDocumentId,
      OriginatingFileToken: prepaymentEntry.OriginatingFileToken
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prepaymentEntry = this.createFromForm();
    if (prepaymentEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.prepaymentEntryService.update(prepaymentEntry));
    } else {
      this.subscribeToSaveResponse(this.prepaymentEntryService.create(prepaymentEntry));
    }
  }

  private createFromForm(): IPrepaymentEntry {
    const entity = {
      ...new PrepaymentEntry(),
      id: this.editForm.get(['id']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      accountName: this.editForm.get(['accountName']).value,
      prepaymentId: this.editForm.get(['prepaymentId']).value,
      prepaymentDate: this.editForm.get(['prepaymentDate']).value,
      particulars: this.editForm.get(['particulars']).value,
      serviceOutlet: this.editForm.get(['serviceOutlet']).value,
      prepaymentAmount: this.editForm.get(['prepaymentAmount']).value,
      months: this.editForm.get(['months']).value,
      supplierName: this.editForm.get(['supplierName']).value,
      invoiceNumber: this.editForm.get(['invoiceNumber']).value,
      scannedDocumentId: this.editForm.get(['scannedDocumentId']).value,
      OriginatingFileToken: this.editForm.get(['OriginatingFileToken']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrepaymentEntry>>) {
    result.subscribe((res: HttpResponse<IPrepaymentEntry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
