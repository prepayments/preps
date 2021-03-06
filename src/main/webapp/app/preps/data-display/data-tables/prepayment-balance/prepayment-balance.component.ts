import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { tap } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { Subject } from 'rxjs/internal/Subject';
import { HttpResponse } from '@angular/common/http';
import { IServiceOutlet } from 'app/shared/model/prepayments/service-outlet.model';
import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { BalanceQuery, IBalanceQuery } from 'app/preps/model/balance-query.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RouteStateService } from 'app/preps/route-state.service';
import { PrepaymentTimeBalanceService } from 'app/preps/data-display/data-tables/prepayment-balance/prepayment-time-balance.service';
import { RegisteredSuppliersReportingService } from 'app/preps/data-display/data-export/registered-suppliers/registered-suppliers-reporting.service';
import { ServiceOutletsReportingService } from 'app/preps/data-display/data-export/service-outlets/service-outlets-reporting.service';
import { TransactionAccountReportingService } from 'app/preps/data-display/data-export/transaction-accounts/transaction-account-reporting.service';

/**
 *  This component displays prepayment balances data as data-tables with full exportation options
 *
 */
@Component({
  selector: 'gha-prepayment-balance',
  templateUrl: './prepayment-balance.component.html',
  styleUrls: ['./prepayment-balance.component.scss']
})
export class PrepaymentBalanceComponent implements OnInit, OnDestroy {
  dtOptions: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();
  prepaymentBalances: any[];
  transactionAccounts: ITransactionAccount[];
  serviceOutlets: IServiceOutlet[];
  suppliers: IRegisteredSupplier[];
  reportDate: string;
  reportServiceOutlet: string;
  reportAccountName: string;
  navigationQuery: IBalanceQuery;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected parseLinks: JhiParseLinks,
    protected router: Router,
    protected jhiAlertService: JhiAlertService,
    private prepaymentTimeBalanceService: PrepaymentTimeBalanceService,
    private registeredSupplierReportingService: RegisteredSuppliersReportingService,
    protected serviceOutletReportingService: ServiceOutletsReportingService,
    protected transactionAccountsReportingService: TransactionAccountReportingService,
    private log: NGXLogger,
    private stateService: RouteStateService<IBalanceQuery>,
    private modalService: NgbModal // For dismissing the balance-query modal
  ) {
    this.updateNavigationQuery();
    this.loadPrepaymentBalancesFirstPass();
    this.dtOptions = this.getDataTableOptions();
  }

  ngOnInit() {
    this.dtOptions = this.getDataTableOptions();
    this.updatePrepaymentBalances();
    this.loadSupportEntities();
  }

  ngOnDestroy(): void {
    this.undefineStuff();
  }

  private getDataTableOptions() {
    return (this.dtOptions = {
      searching: true,
      paging: true,
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      dom: 'Bfrtip',
      buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
    });
  }

  private undefineStuff(): void {
    this.dtOptions = undefined;
    this.dtTrigger.unsubscribe();
    this.prepaymentBalances = undefined;
    this.transactionAccounts = undefined;
    this.serviceOutlets = undefined;
    this.suppliers = undefined;
    this.navigationQuery = undefined;
  }

  private updatePrepaymentBalances(): void {
    if (this.stateService.data !== undefined) {
      this.prepaymentTimeBalanceService.getEntities(this.stateService.data).subscribe(
        res => {
          this.prepaymentBalances = res.body;
          // TODO test whether data-tables are created once and only once
          this.dtTrigger.next();
        },
        err => this.onError(err.toString()),
        () => this.log.info(`Extracted ${this.prepaymentBalances.length} prepayment balances from API`)
      );

      // Update the title
      this.updateReportTitles(this.stateService.data);
    } else {
      this.prepaymentTimeBalanceService.getEntities(this.navigationQuery).subscribe(
        res => {
          this.prepaymentBalances = res.body;
          // TODO test whether data-tables are created once and only once
          this.dtTrigger.next();
        },
        err => this.onError(err.toString()),
        () => this.log.info(`Extracted ${this.prepaymentBalances.length} prepayment balances from API`)
      );

      // Update the title
      this.updateReportTitles(this.navigationQuery);
    }
  }

  private updateReportTitles(balanceQuery: IBalanceQuery): void {
    this.reportDate = balanceQuery.balanceDate.toString();
    this.reportServiceOutlet = balanceQuery.serviceOutlet;
    this.reportAccountName = balanceQuery.accountName;
  }

  private loadSupportEntities() {
    this.serviceOutletReportingService.getEntities().subscribe(
      (data: HttpResponse<IServiceOutlet[]>) => {
        this.serviceOutlets = data.body;
      },
      err => this.onError(err),
      () => {
        this.log.info(`Service outlets array has been primed with ${this.serviceOutlets.length} items`);
      }
    );

    this.transactionAccountsReportingService
      .getEntities()
      .pipe(tap(res => this.log.info(`Transaction accounts array has been primed with  ${res.body.length} transaction items...`)))
      .subscribe(
        (data: HttpResponse<ITransactionAccount[]>) => {
          this.transactionAccounts = data.body;
        },
        err => this.onError(err)
      );

    this.registeredSupplierReportingService
      .getEntities()
      .pipe(tap(res => this.log.info(`Registered suppliers array has been primed with  ${res.body.length} supplier items...`)))
      .subscribe(
        (data: HttpResponse<IRegisteredSupplier[]>) => {
          this.suppliers = data.body;
        },
        err => this.onError(err)
      );
  }

  private loadServiceOutletEntities() {
    this.serviceOutletReportingService.getEntities().subscribe(
      (data: HttpResponse<IServiceOutlet[]>) => {
        this.serviceOutlets = data.body;
      },
      err => this.onError(err),
      () => {
        this.log.info(`Service outlets array has been primed with ${this.serviceOutlets.length} items`);
      }
    );
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

  private loadPrepaymentBalancesFirstPass(): void {
    if (this.stateService.data === undefined) {
      this.prepaymentTimeBalanceService.getEntities(this.navigationQuery).subscribe(
        res => {
          this.prepaymentBalances = res.body;
          // TODO Avoid Creating data-tables twice
          // this.dtTrigger.next();
        },
        err => this.onError(err.toString()),
        () => this.log.info(`Extracted ${this.prepaymentBalances.length} prepayment balances from API`)
      );
    } else {
      this.prepaymentTimeBalanceService.getEntities(this.stateService.data).subscribe(
        res => {
          this.prepaymentBalances = res.body;
          // TODO Avoid Creating data-tables twice
          // this.dtTrigger.next();
        },
        err => this.onError(err.toString()),
        () => this.log.info(`Extracted ${this.prepaymentBalances.length} prepayment balances from API`)
      );
    }
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
    this.log.error(`Error while extracting data from API $errorMessage`);

    this.previousView();
  }

  private previousView() {
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

  private onSelectAccountName(accountName: string): void {
    this.log.info(`Navigating Transaction account whose account name is : ${accountName}`);

    const matchingAccount: ITransactionAccount = this.transactionAccounts.filter((account: ITransactionAccount) => {
      return account.accountName === accountName;
    })[0];

    if (matchingAccount === undefined) {
      // early exit
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

  private onSelectServiceOutletCode(serviceOutletCode: string): void {
    this.log.info(`Navigating service outlet whose code is : ${serviceOutletCode}`);

    const matchingSol: IServiceOutlet = this.serviceOutlets.filter((sol: IServiceOutlet) => {
      return sol.serviceOutletCode === serviceOutletCode;
    })[0];

    const solId: number = matchingSol.id;

    this.log.info(`Navigating ...`);

    this.router.navigate(['/service-outlet', solId, 'view']).then(fulfilled => {
      if (fulfilled) {
        this.log.info(`successfully navigated to sol # : ${solId}`);
      } else {
        this.onError(`Could not find service outlet id: ${solId}`);
      }
    });
  }

  private onSelectSupplierName(supplierName: string): void {
    this.log.info(`Navigating to supplier whose name is : ${supplierName}`);

    const matchingItem: IRegisteredSupplier = this.suppliers.filter((supplier: IRegisteredSupplier) => {
      return supplier.supplierName === supplierName;
    })[0];

    const supplierId: number = matchingItem.id;

    this.log.info(`Navigating ...`);

    this.router.navigate(['/registered-supplier', supplierId, 'view']).then(fulfilled => {
      if (fulfilled) {
        this.log.info(`successfully navigated to supplier id # : ${supplierId}`);
      } else {
        this.onError(`Could not find supplier who id: ${supplierId}`);
      }
    });
  }
}
