import { Component, OnInit } from '@angular/core';
import { BalanceQueryModalService } from 'app/preps/gha-balance-query/balance-query-modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'gha-dashboard-hud',
  templateUrl: './dashboard-hud.component.html',
  styleUrls: ['./dashboard-hud.component.scss']
})
export class DashboardHudComponent implements OnInit {
  modalRef: NgbModalRef;

  constructor(private balanceQueryModalService: BalanceQueryModalService) {}

  ngOnInit() {}

  navigateToDate(): void {
    this.modalRef = this.balanceQueryModalService.open();
  }
}
