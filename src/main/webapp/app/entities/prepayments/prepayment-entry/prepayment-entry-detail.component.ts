import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

@Component({
  selector: 'gha-prepayment-entry-detail',
  templateUrl: './prepayment-entry-detail.component.html'
})
export class PrepaymentEntryDetailComponent implements OnInit {
  prepaymentEntry: IPrepaymentEntry;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prepaymentEntry }) => {
      this.prepaymentEntry = prepaymentEntry;
    });
  }

  previousState() {
    window.history.back();
  }
}
