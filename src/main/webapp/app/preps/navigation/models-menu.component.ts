import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'gha-models-menu',
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
          <fa-icon icon="hdd"></fa-icon>
          <span>
            Models
          </span>
        </span>
      </a>
      <ul class="dropdown-menu" ngbDropdownMenu aria-labelledby="entity-menu">
        <li>
          <a
            class="dropdown-item"
            routerLink="amortization-entry"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Amortization Entry</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="amortization-upload-file"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Amortization Upload File</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="prepayment-entry"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Prepayment Entry</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="accounting-transaction"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Accounting Transaction</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="service-outlet"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Service Outlet</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="registered-supplier"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Registered Supplier</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="transaction-account"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Transaction Account</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="scanned-document"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Scanned Document</span>
          </a>
        </li>
      </ul>
    </li>
  `,
  styles: []
})
export class ModelsMenuComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
