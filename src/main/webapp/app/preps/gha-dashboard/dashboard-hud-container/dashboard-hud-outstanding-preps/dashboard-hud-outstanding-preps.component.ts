import { Component, OnInit } from '@angular/core';
import { BalanceQueryModalService } from 'app/preps/gha-balance-query/balance-query-modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NavigationExtras, Router } from '@angular/router';
import * as moment from 'moment';

@Component({
  selector: 'gha-dashboard-hud-outstanding-preps',
  templateUrl: './dashboard-hud-outstanding-preps.component.html',
  styleUrls: ['./dashboard-hud-outstanding-preps.component.scss']
})
export class DashboardHudOutstandingPrepsComponent implements OnInit {
  modalRef: NgbModalRef;

  constructor(private balanceQueryModalService: BalanceQueryModalService, private router: Router) {}

  ngOnInit() {}

  protected navigateToDate(): void {
    this.modalRef = this.balanceQueryModalService.open();
  }

  protected navigateToDay(): void {
    const navigationExtras: NavigationExtras = {
      queryParams: {
        balanceDate: moment().format('YYYY-MM-DD'),
        serviceOutlet: 'All',
        accountName: 'All'
      }
    };

    this.router.navigate(['data-tables/prepayment-balances'], navigationExtras);
  }
}
