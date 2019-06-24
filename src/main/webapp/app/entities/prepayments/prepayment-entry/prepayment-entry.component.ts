import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { tap } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PrepaymentEntryService } from './prepayment-entry.service';
import { IServiceOutlet } from 'app/shared/model/prepayments/service-outlet.model';
import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';
import { TransactionAccountReportingService } from 'app/preps/data-export/transaction-accounts/transaction-account-reporting.service';
import { ServiceOutletsReportingService } from 'app/preps/data-export/service-outlets/service-outlets-reporting.service';
import { NGXLogger } from 'ngx-logger';
import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { RegisteredSuppliersReportingService } from 'app/preps/data-export/registered-suppliers/registered-suppliers-reporting.service';

@Component({
  selector: 'gha-prepayment-entry',
  templateUrl: './prepayment-entry.component.html'
})
export class PrepaymentEntryComponent implements OnInit, OnDestroy {
  prepaymentEntries: IPrepaymentEntry[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  serviceOutlets: IServiceOutlet[];
  transactionAccounts: ITransactionAccount[];
  registeredSuppliers: IRegisteredSupplier[];

  constructor(
    protected prepaymentEntryService: PrepaymentEntryService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    protected log: NGXLogger,
    protected router: Router,
    protected transactionAccountsReportingService: TransactionAccountReportingService,
    protected serviceOutletReportingService: ServiceOutletsReportingService,
    protected registeredSuppliersReportingService: RegisteredSuppliersReportingService
  ) {
    this.prepaymentEntries = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.prepaymentEntryService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IPrepaymentEntry[]>) => this.paginatePrepaymentEntries(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }

    this.prepaymentEntryService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPrepaymentEntry[]>) => this.paginatePrepaymentEntries(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
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

    this.registeredSuppliersReportingService.getEntities().subscribe(
      (data: HttpResponse<IRegisteredSupplier[]>) => {
        this.registeredSuppliers = data.body;
      },
      err => this.onError(err),
      () => {
        this.log.info(`Registered suppliers array has been primed with ${this.registeredSuppliers.length} items...`);
      }
    );
  }

  reset() {
    this.page = 0;
    this.prepaymentEntries = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.prepaymentEntries = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch = '';
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.prepaymentEntries = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPrepaymentEntries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrepaymentEntry) {
    return item.id;
  }

  registerChangeInPrepaymentEntries() {
    this.eventSubscriber = this.eventManager.subscribe('prepaymentEntryListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePrepaymentEntries(data: IPrepaymentEntry[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.prepaymentEntries.push(data[i]);
    }
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
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

  private onSelectRegisteredSupplier(supplierName: string): void {
    this.log.info(`Navigating to Supplier entity whose name is : ${supplierName}`);

    const matchingEntity: IRegisteredSupplier = this.registeredSuppliers.filter((supplier: IRegisteredSupplier) => {
      return supplier.supplierName === supplierName;
    })[0];

    if (matchingEntity === undefined) {
      // early exit
      return;
    }

    const supplierId: number = matchingEntity.id;

    this.log.info(`Navigating ...`);

    this.router.navigate(['/registered-supplier', supplierId, 'view']).then(fulfilled => {
      if (fulfilled) {
        this.log.info(`successfully navigated to supplier# : ${supplierId}`);
      } else {
        this.onError(`Could not find supplier#: ${supplierId}`);
      }
    });
  }
}
