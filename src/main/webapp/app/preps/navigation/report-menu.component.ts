import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'gha-report-menu',
    template: `
        <div *ghaHasAnyAuthority="'ROLE_USER'" ngbDropdown class="nav-item dropdown pointer" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
            <a class="nav-link dropdown-toggle" ngbDropdownToggle href="javascript:void(0);" id="reports-menu">
                    <span>
                        <fa-icon icon="book"></fa-icon>
                        <span>
                            Reports
                        </span>
                    </span>
            </a>
            <ul class="dropdown-menu" ngbDropdownMenu aria-labelledby="reports-menu">
                <li>
                    <a class="dropdown-item" routerLink="reporting/amortization-entries" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }" (click)="collapseNavbar()">
                        <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
                        <span>Amortization Entries</span>
                    </a>
                </li>
                <li>
                    <a class="dropdown-item" routerLink="reporting/prepayment-entries" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }" (click)="collapseNavbar()">
                        <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
                        <span>Prepayment Entries</span>
                    </a>
                </li>
                <li>
                    <a class="dropdown-item" routerLink="reporting/registered-suppliers" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }" (click)="collapseNavbar()">
                        <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
                        <span>Registered Suppliers</span>
                    </a>
                </li>
                <li>
                    <a class="dropdown-item" routerLink="reporting/service-outlets" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }" (click)="collapseNavbar()">
                        <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
                        <span>Service Outlets</span>
                    </a>
                </li>
                <li>
                    <a class="dropdown-item" routerLink="reporting/transaction-accounts" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }" (click)="collapseNavbar()">
                        <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
                        <span>Transaction Accounts</span>
                    </a>
                </li>
                <ul *ghaHasAnyAuthority="'ROLE_ADMIN'" ngbDropdown class="nav-item dropdown pointer" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
                  <li>
                    <a class="dropdown-item" routerLink="report-type" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }" (click)="collapseNavbar()">
                      <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
                      <span>Report Type</span>
                    </a>
                  </li>
                  <li>
                    <a class="dropdown-item" routerLink="report-request-event" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }" (click)="collapseNavbar()">
                      <fa-icon icon="asterisk" fixedWidth="true"></fa-icon>
                      <span>Report Request Event</span>
                    </a>
                  </li>
                </ul>
            </ul>
        </div>
    `,
    styles: []
})
export class ReportMenuComponent implements OnInit {
    constructor() {}

    ngOnInit() {}
}
