import { Component, OnInit } from '@angular/core';
import { IAmortizationSchedule } from 'app/preps/model/amortization-schedule';
import { Subject } from 'rxjs/internal/Subject';
import { NGXLogger } from 'ngx-logger';
import { ActivatedRoute, Router } from '@angular/router';
import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';
import { AmortizationScheduleService } from 'app/preps/data-display/data-tables/prepayment-amortization/amortization-schedule.service';
import { JhiAlertService } from 'ng-jhipster';
import { TransactionAccountService } from 'app/entities/prepayments/transaction-account';
import { tap } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { TransactionAccountReportingService } from 'app/preps/data-display/data-export/transaction-accounts/transaction-account-reporting.service';
import { RouteStateService } from 'app/preps/route-state.service';
import { BalanceQuery, IBalanceQuery } from 'app/preps/model/balance-query.model';

@Component({
  selector: 'gha-amortization-schedule',
  templateUrl: './amortization-schedule.component.html',
  styleUrls: ['./amortization-schedule.component.scss']
})
export class AmortizationScheduleComponent implements OnInit {
  dtOptions: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();
  amortizationScheduleArray: IAmortizationSchedule[];
  reportDate: string;
  navigationQuery: IBalanceQuery;

  // Navigation data
  transactionAccounts: ITransactionAccount[];

  constructor(
    private log: NGXLogger,
    private router: Router,
    private jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    private routerStateService: RouteStateService<IBalanceQuery>,
    private transactionAccountService: TransactionAccountService,
    private amortizationScheduleService: AmortizationScheduleService,
    protected transactionAccountsReportingService: TransactionAccountReportingService
  ) {
    this.updateNavigationQuery();
    this.loadAmortizationScheduleFirstPass();
  }

  ngOnInit() {
    this.loadAmortizationSchedule();
    this.loadSupportEntitie();
  }

  private updateNavigationQuery(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.navigationQuery = new BalanceQuery({
        balanceDate: params['balanceDate'],
        serviceOutlet: params['serviceOutlet'],
        accountName: params['accountName']
      });
    });
  }

  private loadAmortizationScheduleFirstPass(): void {
    this.dtOptions = this.getDataTableOptions();
    this.amortizationScheduleService.query(this.routerStateService.data).subscribe(
      res => {
        this.amortizationScheduleArray = res.body;
        // TODO Avoid accidentally triggering the table while priming its data
        // this.dtTrigger.next();
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.amortizationScheduleArray.length} amortization schedule items from API`)
    );
  }

  private loadAmortizationSchedule(): void {
    this.dtOptions = this.getDataTableOptions();
    if (this.routerStateService.data !== undefined) {
      this.amortizationScheduleService.query(this.routerStateService.data).subscribe(
        res => {
          this.amortizationScheduleArray = res.body;
          this.dtTrigger.next();
        },
        err => this.onError(err.toString()),
        () => this.log.info(`Extracted ${this.amortizationScheduleArray.length} amortization schedule items from API`)
      );
      // Update the title
      this.updateReportTitles(this.routerStateService.data);
    } else {
      this.amortizationScheduleService.query(this.navigationQuery).subscribe(
        res => {
          this.amortizationScheduleArray = res.body;
          // TODO test whether data-tables are created once and only once
          this.dtTrigger.next();
        },
        err => this.onError(err.toString()),
        () => this.log.info(`Extracted ${this.amortizationScheduleArray.length} amortization schedule items from API`)
      );

      // Update the title
      this.updateReportTitles(this.navigationQuery);
    }
  }

  private updateReportTitles(balanceQuery: IBalanceQuery): void {
    this.reportDate = balanceQuery.balanceDate.toString();
  }

  private loadSupportEntitie(): void {
    // TODO Load array to enable navigation to other views
    this.transactionAccountsReportingService
      .getEntities()
      .pipe(tap(res => this.log.info(`Transaction accounts array has been primed with  ${res.body.length} transaction items...`)))
      .subscribe(
        (data: HttpResponse<ITransactionAccount[]>) => {
          this.transactionAccounts = data.body;
        },
        err => this.onError(err)
      );
  }

  private getDataTableOptions() {
    return (this.dtOptions = {
      searching: true,
      paging: true,
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      dom: 'Bfrtip',
      buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
    });
  }

  protected previousView() {
    const navigation = this.router.navigate(['data-tables/prepayment-balances']);
    navigation.then(() => {
      this.log.debug(`Well! This was not supposed to happen. Review request parameters and reiterate`);
    });
  }

  private onSelectAccountNumber(accountNumber: string): void {
    this.log.info(`Navigating Transaction account whose account# is : ${accountNumber}`);

    const matchingAccount: ITransactionAccount = this.transactionAccounts.filter((account: ITransactionAccount) => {
      return account.accountNumber === accountNumber;
    })[0];

    if (matchingAccount === undefined) {
      return;
    }

    const accountId: number = matchingAccount.id;

    this.log.info(`Navigating ...`);

    this.router.navigate(['/transaction-account', accountId, 'view']).then(fulfilled => {
      if (fulfilled) {
        this.log.info(`successfully navigated to account# : ${accountId}`);
      } else {
        this.onError(`Could not find account#: ${accountId}`);
      }
    });
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
    this.log.error(`Error while extracting data from API $errorMessage`);

    this.previousView();
  }
}
