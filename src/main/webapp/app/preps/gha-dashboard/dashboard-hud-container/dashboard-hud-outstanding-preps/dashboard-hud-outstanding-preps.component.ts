import { Component, OnInit } from '@angular/core';
import { BalanceQueryModalService } from 'app/preps/gha-balance-query/balance-query-modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NavigationExtras, Router } from '@angular/router';
import * as moment from 'moment';
import { NGXLogger } from 'ngx-logger';
import { OutstandingBalanceService } from 'app/preps/gha-dashboard/dashboard-hud-container/dashboard-hud-outstanding-preps/outstanding-balance.service';
import { IBalanceQuery } from 'app/preps/model/balance-query.model';

@Component({
  selector: 'gha-dashboard-hud-outstanding-preps',
  templateUrl: './dashboard-hud-outstanding-preps.component.html',
  styleUrls: ['./dashboard-hud-outstanding-preps.component.scss']
})
export class DashboardHudOutstandingPrepsComponent implements OnInit {
  modalRef: NgbModalRef;
  outstandingBalanceAmount: number;

  constructor(
    private balanceQueryModalService: BalanceQueryModalService,
    private outstandingBalanceService: OutstandingBalanceService,
    private router: Router,
    private log: NGXLogger
  ) {}

  ngOnInit() {
    const todate: string = moment().format('YYYY-MM-DD');
    const balanceQuery: IBalanceQuery = {
      balanceDate: moment(),
      serviceOutlet: 'All',
      accountName: 'All'
    };
    this.outstandingBalanceService.queryAmount(balanceQuery).subscribe(balanceAmount => {
      this.outstandingBalanceAmount = balanceAmount / 1000000;
    });

    this.outstandingBalanceAmount.toFixed(2);
  }

  protected navigateToDate(): void {
    this.modalRef = this.balanceQueryModalService.open();
  }

  protected navigateToDay(): void {
    const todate: string = moment().format('YYYY-MM-DD');
    const navigationExtras: NavigationExtras = {
      queryParams: {
        balanceDate: todate,
        serviceOutlet: 'All',
        accountName: 'All'
      }
    };

    this.router
      .navigate(['data-tables/prepayment-balances'], navigationExtras)
      .then(() => {
        this.log.debug(`Successfully navigated to prepayment-balances as at ${todate}`);
      })
      .catch(() => {
        this.log.debug(`This is embarrassing. The system has failed to navigate to prepayment-balances as at ${todate}`);
      });
  }
}
