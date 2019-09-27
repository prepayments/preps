import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';

@Component({
  selector: 'gha-amortization-entry-update-detail',
  templateUrl: './amortization-entry-update-detail.component.html'
})
export class AmortizationEntryUpdateDetailComponent implements OnInit {
  amortizationEntryUpdate: IAmortizationEntryUpdate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationEntryUpdate }) => {
      this.amortizationEntryUpdate = amortizationEntryUpdate;
    });
  }

  previousState() {
    window.history.back();
  }
}
