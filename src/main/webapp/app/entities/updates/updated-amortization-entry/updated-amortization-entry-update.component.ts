import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IUpdatedAmortizationEntry, UpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';
import { UpdatedAmortizationEntryService } from './updated-amortization-entry.service';
import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';
import { PrepaymentEntryService } from 'app/entities/prepayments/prepayment-entry';

@Component({
  selector: 'gha-updated-amortization-entry-update',
  templateUrl: './updated-amortization-entry-update.component.html'
})
export class UpdatedAmortizationEntryUpdateComponent implements OnInit {
  updatedAmortizationEntry: IUpdatedAmortizationEntry;
  isSaving: boolean;

  prepaymententries: IPrepaymentEntry[];
  amortizationDateDp: any;
  dateOfUpdateDp: any;

  editForm = this.fb.group({
    id: [],
    amortizationDate: [null, [Validators.required]],
    amortizationAmount: [null, [Validators.required]],
    particulars: [],
    prepaymentServiceOutlet: [null, [Validators.required]],
    prepaymentAccountNumber: [null, [Validators.required]],
    amortizationServiceOutlet: [null, [Validators.required]],
    amortizationAccountNumber: [null, [Validators.required]],
    originatingFileToken: [],
    amortizationTag: [],
    orphaned: [],
    dateOfUpdate: [null, [Validators.required]],
    reasonForUpdate: [],
    prepaymentEntryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected updatedAmortizationEntryService: UpdatedAmortizationEntryService,
    protected prepaymentEntryService: PrepaymentEntryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ updatedAmortizationEntry }) => {
      this.updateForm(updatedAmortizationEntry);
      this.updatedAmortizationEntry = updatedAmortizationEntry;
    });
    this.prepaymentEntryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPrepaymentEntry[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPrepaymentEntry[]>) => response.body)
      )
      .subscribe((res: IPrepaymentEntry[]) => (this.prepaymententries = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(updatedAmortizationEntry: IUpdatedAmortizationEntry) {
    this.editForm.patchValue({
      id: updatedAmortizationEntry.id,
      amortizationDate: updatedAmortizationEntry.amortizationDate,
      amortizationAmount: updatedAmortizationEntry.amortizationAmount,
      particulars: updatedAmortizationEntry.particulars,
      prepaymentServiceOutlet: updatedAmortizationEntry.prepaymentServiceOutlet,
      prepaymentAccountNumber: updatedAmortizationEntry.prepaymentAccountNumber,
      amortizationServiceOutlet: updatedAmortizationEntry.amortizationServiceOutlet,
      amortizationAccountNumber: updatedAmortizationEntry.amortizationAccountNumber,
      originatingFileToken: updatedAmortizationEntry.originatingFileToken,
      amortizationTag: updatedAmortizationEntry.amortizationTag,
      orphaned: updatedAmortizationEntry.orphaned,
      dateOfUpdate: updatedAmortizationEntry.dateOfUpdate,
      reasonForUpdate: updatedAmortizationEntry.reasonForUpdate,
      prepaymentEntryId: updatedAmortizationEntry.prepaymentEntryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const updatedAmortizationEntry = this.createFromForm();
    if (updatedAmortizationEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.updatedAmortizationEntryService.update(updatedAmortizationEntry));
    } else {
      this.subscribeToSaveResponse(this.updatedAmortizationEntryService.create(updatedAmortizationEntry));
    }
  }

  private createFromForm(): IUpdatedAmortizationEntry {
    const entity = {
      ...new UpdatedAmortizationEntry(),
      id: this.editForm.get(['id']).value,
      amortizationDate: this.editForm.get(['amortizationDate']).value,
      amortizationAmount: this.editForm.get(['amortizationAmount']).value,
      particulars: this.editForm.get(['particulars']).value,
      prepaymentServiceOutlet: this.editForm.get(['prepaymentServiceOutlet']).value,
      prepaymentAccountNumber: this.editForm.get(['prepaymentAccountNumber']).value,
      amortizationServiceOutlet: this.editForm.get(['amortizationServiceOutlet']).value,
      amortizationAccountNumber: this.editForm.get(['amortizationAccountNumber']).value,
      originatingFileToken: this.editForm.get(['originatingFileToken']).value,
      amortizationTag: this.editForm.get(['amortizationTag']).value,
      orphaned: this.editForm.get(['orphaned']).value,
      dateOfUpdate: this.editForm.get(['dateOfUpdate']).value,
      reasonForUpdate: this.editForm.get(['reasonForUpdate']).value,
      prepaymentEntryId: this.editForm.get(['prepaymentEntryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUpdatedAmortizationEntry>>) {
    result.subscribe(
      (res: HttpResponse<IUpdatedAmortizationEntry>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPrepaymentEntryById(index: number, item: IPrepaymentEntry) {
    return item.id;
  }
}
