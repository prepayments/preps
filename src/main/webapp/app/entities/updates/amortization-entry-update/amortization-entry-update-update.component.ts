import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IAmortizationEntryUpdate, AmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';
import { AmortizationEntryUpdateService } from './amortization-entry-update.service';

@Component({
  selector: 'gha-amortization-entry-update-update',
  templateUrl: './amortization-entry-update-update.component.html'
})
export class AmortizationEntryUpdateUpdateComponent implements OnInit {
  amortizationEntryUpdate: IAmortizationEntryUpdate;
  isSaving: boolean;
  amortizationDateDp: any;

  editForm = this.fb.group({
    id: [],
    amortizationEntryId: [null, [Validators.required]],
    amortizationDate: [null, [Validators.required]],
    amortizationAmount: [null, [Validators.required]],
    particulars: [],
    prepaymentServiceOutlet: [null, [Validators.required]],
    prepaymentAccountNumber: [null, [Validators.required]],
    amortizationServiceOutlet: [null, [Validators.required]],
    amortizationAccountNumber: [null, [Validators.required]],
    prepaymentEntryId: [null, [Validators.required]],
    originatingFileToken: [],
    amortizationTag: [],
    orphaned: []
  });

  constructor(
    protected amortizationEntryUpdateService: AmortizationEntryUpdateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amortizationEntryUpdate }) => {
      this.updateForm(amortizationEntryUpdate);
      this.amortizationEntryUpdate = amortizationEntryUpdate;
    });
  }

  updateForm(amortizationEntryUpdate: IAmortizationEntryUpdate) {
    this.editForm.patchValue({
      id: amortizationEntryUpdate.id,
      amortizationEntryId: amortizationEntryUpdate.amortizationEntryId,
      amortizationDate: amortizationEntryUpdate.amortizationDate,
      amortizationAmount: amortizationEntryUpdate.amortizationAmount,
      particulars: amortizationEntryUpdate.particulars,
      prepaymentServiceOutlet: amortizationEntryUpdate.prepaymentServiceOutlet,
      prepaymentAccountNumber: amortizationEntryUpdate.prepaymentAccountNumber,
      amortizationServiceOutlet: amortizationEntryUpdate.amortizationServiceOutlet,
      amortizationAccountNumber: amortizationEntryUpdate.amortizationAccountNumber,
      prepaymentEntryId: amortizationEntryUpdate.prepaymentEntryId,
      originatingFileToken: amortizationEntryUpdate.originatingFileToken,
      amortizationTag: amortizationEntryUpdate.amortizationTag,
      orphaned: amortizationEntryUpdate.orphaned
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const amortizationEntryUpdate = this.createFromForm();
    if (amortizationEntryUpdate.id !== undefined) {
      this.subscribeToSaveResponse(this.amortizationEntryUpdateService.update(amortizationEntryUpdate));
    } else {
      this.subscribeToSaveResponse(this.amortizationEntryUpdateService.create(amortizationEntryUpdate));
    }
  }

  private createFromForm(): IAmortizationEntryUpdate {
    const entity = {
      ...new AmortizationEntryUpdate(),
      id: this.editForm.get(['id']).value,
      amortizationEntryId: this.editForm.get(['amortizationEntryId']).value,
      amortizationDate: this.editForm.get(['amortizationDate']).value,
      amortizationAmount: this.editForm.get(['amortizationAmount']).value,
      particulars: this.editForm.get(['particulars']).value,
      prepaymentServiceOutlet: this.editForm.get(['prepaymentServiceOutlet']).value,
      prepaymentAccountNumber: this.editForm.get(['prepaymentAccountNumber']).value,
      amortizationServiceOutlet: this.editForm.get(['amortizationServiceOutlet']).value,
      amortizationAccountNumber: this.editForm.get(['amortizationAccountNumber']).value,
      prepaymentEntryId: this.editForm.get(['prepaymentEntryId']).value,
      originatingFileToken: this.editForm.get(['originatingFileToken']).value,
      amortizationTag: this.editForm.get(['amortizationTag']).value,
      orphaned: this.editForm.get(['orphaned']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationEntryUpdate>>) {
    result.subscribe((res: HttpResponse<IAmortizationEntryUpdate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
