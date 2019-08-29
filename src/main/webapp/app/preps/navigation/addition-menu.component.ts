import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'gha-addition-menu',
  template: `
    <li
      *ghaHasAnyAuthority="'ROLE_USER'"
      ngbDropdown
      class="nav-item dropdown pointer"
      routerLinkActive="active"
      [routerLinkActiveOptions]="{ exact: true }"
    >
      <a class="nav-link dropdown-toggle" ngbDropdownToggle href="javascript:void(0);" id="entity-menu">
        <span>
          <fa-icon icon="plus"></fa-icon>
          <span>
            Add
          </span>
        </span>
      </a>
      <ul class="dropdown-menu" ngbDropdownMenu aria-labelledby="entity-menu">
        <li>
          <a
            class="dropdown-item"
            routerLink="amortization-entry/new"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Amortization Entry</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="amortization-upload/new"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Amortization Upload</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" routerLink="prepayment-entry/new" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Prepayment Entry</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="accounting-transaction/new"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Accounting Transaction</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" routerLink="service-outlet/new" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Service Outlet</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="registered-supplier/new"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Registered Supplier</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="transaction-account/new"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Transaction Account</span>
          </a>
        </li>
        <li>
          <a class="dropdown-item" routerLink="scanned-document/new" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }">
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Scanned Document</span>
          </a>
        </li>
      </ul>
    </li>
  `,
  styles: []
})
export class AdditionMenuComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
