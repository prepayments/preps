import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RouteStateService } from 'app/preps/route-state.service';
import { IBalanceQuery } from 'app/preps/model/balance-query.model';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiAlertService } from 'ng-jhipster';
import { AmortizationEntry, IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

@Component({
  selector: 'gha-amortization-schedule-query',
  templateUrl: './amortization-schedule-query.component.html',
  styleUrls: ['./amortization-schedule-query.component.scss']
})
export class AmortizationScheduleQueryComponent implements OnInit {
  private pageUrl = '';

  isSaving: boolean;

  balanceQuery: IBalanceQuery;

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
    prepaymentEntryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    private fb: FormBuilder,
    private router: Router,
    private routeStateService: RouteStateService<IBalanceQuery>
  ) {}

  ngOnInit() {}

  navigateToSchedule() {
    const amortizationEntry = this.createFromForm();

    // TODO update service data this.routeStateService.data = //

    // TODO Navigate
    this.router.navigateByUrl(this.pageUrl);
  }

  private createFromForm(): IAmortizationEntry {
    const entity = {
      ...new AmortizationEntry(),
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
      prepaymentEntryId: this.editForm.get(['prepaymentEntryId']).value
    };
    return entity;
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

  previousState() {
    window.history.back();
  }
}
