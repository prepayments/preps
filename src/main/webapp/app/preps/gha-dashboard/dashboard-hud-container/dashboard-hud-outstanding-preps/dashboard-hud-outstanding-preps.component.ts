import { Component, OnInit } from '@angular/core';
import { BalanceQueryModalService } from 'app/preps/gha-balance-query/balance-query-modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'gha-dashboard-hud-outstanding-preps',
  templateUrl: './dashboard-hud-outstanding-preps.component.html',
  styleUrls: ['./dashboard-hud-outstanding-preps.component.scss']
})
export class DashboardHudOutstandingPrepsComponent implements OnInit {
  modalRef: NgbModalRef;

  constructor(private balanceQueryModalService: BalanceQueryModalService) {}

  ngOnInit() {}

  navigateToDate(): void {
    this.modalRef = this.balanceQueryModalService.open();
  }
}
