import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'gha-import-menu',
  template: `
    <li
      *ghaHasAnyAuthority="'ROLE_ADMIN'"
      ngbDropdown
      class="nav-item dropdown pointer"
      routerLinkActive="active"
      [routerLinkActiveOptions]="{ exact: true }"
    >
      <a class="nav-link dropdown-toggle" ngbDropdownToggle href="javascript:void(0);" id="admin-menu">
        <span>
          <fa-icon icon="cloud"></fa-icon>
          <span>Import</span>
        </span>
      </a>
      <ul class="dropdown-menu" ngbDropdownMenu aria-labelledby="admin-menu">
        <li>
          <a
            class="dropdown-item"
            routerLink="amortization-data-entry-file"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Amortization Data Entry File</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="prepayment-data-entry-file"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Prepayment Data Entry File</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="supplier-data-entry-file"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Supplier Data Entry File</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="service-outlet-data-entry-file"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Service Outlet Data Entry File</span>
          </a>
        </li>
        <li>
          <a
            class="dropdown-item"
            routerLink="transaction-account-data-entry-file"
            routerLinkActive="active"
            [routerLinkActiveOptions]="{ exact: true }"
            (click)="collapseNavbar()"
          >
            <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
            <span>Transaction Account Data Entry File</span>
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
      </ul>
    </li>
  `,
  styles: []
})
export class ImportMenuComponent implements OnInit {
  isNavbarCollapsed: boolean;

  constructor() {
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {}

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }
}
