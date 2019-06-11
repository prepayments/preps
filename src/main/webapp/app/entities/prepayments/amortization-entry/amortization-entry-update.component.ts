import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAmortizationEntry, AmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';
import { AmortizationEntryService } from './amortization-entry.service';
import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';
import { PrepaymentEntryService } from 'app/entities/prepayments/prepayment-entry';

@Component({
  selector: 'gha-amortization-entry-update',
  templateUrl: './amortization-entry-update.component.html'
})
export class AmortizationEntryUpdateComponent implements OnInit {
  amortizationEntry: IAmortizationEntry;
  isSaving: boolean;

  prepaymententries: IPrepaymentEntry[];
  amortizationDateDp: any;

  editForm = this.fb.group({
    id: [],
    amortizationDate: [null, [Validators.required]],
    amortizationAmount: [null, [Validators.required]],
    particulars: [],
    posted: [],
    serviceOutlet: [null, [Validators.required, Validators.pattern('^[0-9]{3}$')]],
    accountNumber: [null, [Validators.required, Validators.pattern('^[0-9]{10,16}$')]],
    accountName: [null, [Validators.required]],
    prepaymentEntryId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected amortizationEntryService: AmortizationEntryService,
    protected prepaymentEntryService: PrepaymentEntryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amortizationEntry }) => {
      this.updateForm(amortizationEntry);
      this.amortizationEntry = amortizationEntry;
    });
    this.prepaymentEntryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPrepaymentEntry[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPrepaymentEntry[]>) => response.body)
      )
      .subscribe((res: IPrepaymentEntry[]) => (this.prepaymententries = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(amortizationEntry: IAmortizationEntry) {
    this.editForm.patchValue({
      id: amortizationEntry.id,
      amortizationDate: amortizationEntry.amortizationDate,
      amortizationAmount: amortizationEntry.amortizationAmount,
      particulars: amortizationEntry.particulars,
      posted: amortizationEntry.posted,
      serviceOutlet: amortizationEntry.serviceOutlet,
      accountNumber: amortizationEntry.accountNumber,
      accountName: amortizationEntry.accountName,
      prepaymentEntryId: amortizationEntry.prepaymentEntryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const amortizationEntry = this.createFromForm();
    if (amortizationEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.amortizationEntryService.update(amortizationEntry));
    } else {
      this.subscribeToSaveResponse(this.amortizationEntryService.create(amortizationEntry));
    }
  }

  private createFromForm(): IAmortizationEntry {
    const entity = {
      ...new AmortizationEntry(),
      id: this.editForm.get(['id']).value,
      amortizationDate: this.editForm.get(['amortizationDate']).value,
      amortizationAmount: this.editForm.get(['amortizationAmount']).value,
      particulars: this.editForm.get(['particulars']).value,
      posted: this.editForm.get(['posted']).value,
      serviceOutlet: this.editForm.get(['serviceOutlet']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      accountName: this.editForm.get(['accountName']).value,
      prepaymentEntryId: this.editForm.get(['prepaymentEntryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationEntry>>) {
    result.subscribe((res: HttpResponse<IAmortizationEntry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
