import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RegisteredSupplierService } from './registered-supplier.service';

@Component({
  selector: 'gha-registered-supplier',
  templateUrl: './registered-supplier.component.html'
})
export class RegisteredSupplierComponent implements OnInit, OnDestroy {
  registeredSuppliers: IRegisteredSupplier[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  constructor(
    protected registeredSupplierService: RegisteredSupplierService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.registeredSuppliers = [];
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
      this.registeredSupplierService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IRegisteredSupplier[]>) => this.paginateRegisteredSuppliers(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.registeredSupplierService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IRegisteredSupplier[]>) => this.paginateRegisteredSuppliers(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  reset() {
    this.page = 0;
    this.registeredSuppliers = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.registeredSuppliers = [];
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
    this.registeredSuppliers = [];
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
    this.registerChangeInRegisteredSuppliers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRegisteredSupplier) {
    return item.id;
  }

  registerChangeInRegisteredSuppliers() {
    this.eventSubscriber = this.eventManager.subscribe('registeredSupplierListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRegisteredSuppliers(data: IRegisteredSupplier[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.registeredSuppliers.push(data[i]);
    }
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
