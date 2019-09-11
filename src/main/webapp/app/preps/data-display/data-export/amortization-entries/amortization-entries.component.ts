import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IServiceOutlet, ServiceOutlet } from 'app/shared/model/prepayments/service-outlet.model';
import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';
import { tap } from 'rxjs/operators';
import { AmortizationReportingService } from 'app/preps/data-display/data-export/amortization-entries/amortization-reporting.service';
import { TransactionAccountReportingService } from 'app/preps/data-display/data-export/transaction-accounts/transaction-account-reporting.service';
import { ServiceOutletsReportingService } from 'app/preps/data-display/data-export/service-outlets/service-outlets-reporting.service';

@Component({
  selector: 'gha-amortization-entries',
  templateUrl: './amortization-entries.component.html',
  styleUrls: ['./amortization-entries.component.scss']
})
export class AmortizationEntriesComponent implements OnInit, OnDestroy {
  dtOptions: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();

  amortizationEntries$: any[];
  amortizationEntries: any[];

  // reference list for service outlets
  serviceOutlets: IServiceOutlet[];

  // reference list for accounts
  transactionAccounts: ITransactionAccount[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected jhiAlertService: JhiAlertService,
    protected log: NGXLogger,
    protected amortizationReportingService: AmortizationReportingService,
    protected transactionAccountsReportingService: TransactionAccountReportingService,
    protected serviceOutletReportingService: ServiceOutletsReportingService
  ) {
    // Its loaded faster here than on initilization
    this.dtOptions = {
      searching: true,
      paging: true,
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      dom: 'Bfrtip',
      buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
    };

    // Still the only way to preload the js files
    this.amortizationReportingService.getEntities().subscribe(
      res => {
        this.amortizationEntries = res.body;
        this.dtTrigger.next();
      },
      (res: HttpErrorResponse) => this.onError(res.message),
      () => {
        this.log.info(`First pass polling of amortization entry items to render with the template...`);
      }
    );
  }

  ngOnInit() {
    this.amortizationReportingService.getEntities().subscribe(
      res => {
        this.amortizationEntries = res.body;
        this.dtTrigger.next();
      },
      (res: HttpErrorResponse) => this.onError(res.message),
      () => {
        this.log.info(`Pulled amortization entry items to render with the template...`);
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

  protected onError(errorMessage: string) {
    // tell the wide-eyed user
    this.jhiAlertService.error(errorMessage, null, null);
    // log to console
    this.log.error(`Error has occured while fetching amortization entries ${errorMessage}`);
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
    this.amortizationEntries = [];
    this.amortizationEntries$ = [];
  }
}
