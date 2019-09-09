import { Component, OnInit } from '@angular/core';
import { AccountService, LoginModalService, LoginService } from 'app/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
  selector: 'gha-about-menu',
  template: `
    <li
      ngbDropdown
      class="nav-item dropdown pointer"
      placement="bottom-right"
      routerLinkActive="active"
      [routerLinkActiveOptions]="{ exact: true }"
    >
      <a
        class="nav-link"
        routerLink="about/preps"
        routerLinkActive="active"
        [routerLinkActiveOptions]="{ exact: true }"
        id="about-preps-menu"
      >
        <span>
          <fa-icon icon="eye"></fa-icon>
          <span>
            About
          </span>
        </span>
      </a>
      <!--<ul class="dropdown-menu" ngbDropdownMenu aria-labelledby="account-menu">
        <li>
          <a class="dropdown-item">
            <fa-icon icon="wrench" fixedWidth="true"></fa-icon>
            <span><p>Created with ♥ by Finance Department ☻</p></span>
          </a>
        </li>
        <li>
          <a class="dropdown-item">
            <fa-icon icon="clock" fixedWidth="true"></fa-icon>
            <span
              >Icons made by
              <a
                href="https://www.freepik.com/?__hstc=57440181.1aba53f2bc0eab00c6bb101309e4720c.1558101916210.1558101916210.1558101916210.1&__hssc=57440181.1.1558101916210&__hsfp=254724135"
                title="Freepik"
                >Freepik</a
              >
              from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by
              <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank"
                >CC 3.0 BY FREEPIK</a
              ></span
            >
          </a>
        </li>
      </ul>-->
    </li>
  `,
  styles: []
})
export class AboutMenuComponent implements OnInit {
  isNavbarCollapsed: boolean;
  modalRef: NgbModalRef;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private router: Router
  ) {
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {}

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }
}
