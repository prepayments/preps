import { Component, OnInit } from '@angular/core';
import { AccountService, LoginModalService, LoginService } from 'app/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BalanceQueryModalService } from 'app/preps/gha-balance-query/balance-query-modal.service';
import { AmortizationScheduleModalService } from 'app/preps/data-display/data-tables/prepayment-amortization/amortization-schedule-modal.service';

@Component({
  selector: 'gha-export-menu',
  template: `
    <li
      *ghaHasAnyAuthority="'ROLE_USER'"
      ngbDropdown
      class="nav-item dropdown pointer"
      routerLinkActive="active"
      [routerLinkActiveOptions]="{ exact: true }"
    >
      <a class="nav-link dropdown-toggle" ngbDropdownToggle href="javascript:void(0);" id="admin-menu">
        <span>
          <fa-icon icon="save"></fa-icon>
          <span>Export</span>
        </span>
      </a>
      <ul class="dropdown-menu" ngbDropdownMenu aria-labelledby="admin-menu">
        <li>
          <a class="dropdown-item" routerLink="data-export/amortization-entries" routerLinkActive="active">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Amortization Entries</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" routerLink="data-export/prepayment-entries" routerLinkActive="active">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Prepayment Entries</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" routerLink="data-export/registered-suppliers" routerLinkActive="active">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Registered Suppliers</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" routerLink="data-export/service-outlets" routerLinkActive="active">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Service Outlets</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" routerLink="data-export/transaction-accounts" routerLinkActive="active">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Transaction Accounts</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" (click)="prepaymentBalanceQuery()" routerLinkActive="active">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Prepayment Balances</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" (click)="amortizationScheduleQuery()" routerLinkActive="active">
            <!--<a class="dropdown-item" routerLink="data-tables/amortization-schedule" routerLinkActive="active">-->
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Amortization Schedule</span>
          </a>
        </li>
      </ul>
    </li>
  `,
  styles: []
})
export class ExportMenuComponent implements OnInit {
  isNavbarCollapsed: boolean;
  modalRef: NgbModalRef;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private balanceQueryModalService: BalanceQueryModalService,
    private amortizationScheduleModalService: AmortizationScheduleModalService,
    private router: Router
  ) {
    this.isNavbarCollapsed = false;
  }

  ngOnInit() {}

  prepaymentBalanceQuery() {
    this.modalRef = this.balanceQueryModalService.open();
  }

  amortizationScheduleQuery() {
    this.modalRef = this.amortizationScheduleModalService.open();
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  /*toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }*/
}
