import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RouteStateService } from 'app/preps/route-state.service';
import { BalanceQuery, IBalanceQuery } from 'app/preps/model/balance-query.model';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiAlertService } from 'ng-jhipster';
import { AmortizationEntry, IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

@Component({
  selector: 'gha-amortization-schedule-query',
  templateUrl: './amortization-schedule-query.component.html',
  styleUrls: ['./amortization-schedule-query.component.scss']
})
export class AmortizationScheduleQueryComponent implements OnInit {
  private pageUrl = 'data-tables/amortization-schedule';

  isSaving: boolean;

  balanceQuery: IBalanceQuery;

  editForm = this.fb.group({
    amortizationDate: [null, [Validators.required]],
    prepaymentServiceOutlet: [null, [Validators.required]],
    prepaymentAccountNumber: [null, [Validators.required]]
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
    this.routeStateService.data = amortizationEntry;

    // TODO Navigate
    this.router
      .navigateByUrl(this.pageUrl)
      .catch()
      .then()
      .finally();
  }

  private createFromForm(): IBalanceQuery {
    const entity = {
      ...new BalanceQuery({
        balanceDate: this.editForm.get(['amortizationDate']).value,
        serviceOutlet: this.editForm.get(['prepaymentServiceOutlet']).value,
        accountName: this.editForm.get(['prepaymentAccountNumber']).value
      })
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
