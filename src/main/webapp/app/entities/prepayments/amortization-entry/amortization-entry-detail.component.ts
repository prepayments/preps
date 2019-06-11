import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

@Component({
  selector: 'gha-amortization-entry-detail',
  templateUrl: './amortization-entry-detail.component.html'
})
export class AmortizationEntryDetailComponent implements OnInit {
  amortizationEntry: IAmortizationEntry;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationEntry }) => {
      this.amortizationEntry = amortizationEntry;
    });
  }

  previousState() {
    window.history.back();
  }
}
