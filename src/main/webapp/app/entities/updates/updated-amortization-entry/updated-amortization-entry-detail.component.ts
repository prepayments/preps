import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';

@Component({
  selector: 'gha-updated-amortization-entry-detail',
  templateUrl: './updated-amortization-entry-detail.component.html'
})
export class UpdatedAmortizationEntryDetailComponent implements OnInit {
  updatedAmortizationEntry: IUpdatedAmortizationEntry;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ updatedAmortizationEntry }) => {
      this.updatedAmortizationEntry = updatedAmortizationEntry;
    });
  }

  previousState() {
    window.history.back();
  }
}
