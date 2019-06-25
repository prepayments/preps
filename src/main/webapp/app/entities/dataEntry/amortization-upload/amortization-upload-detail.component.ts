import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';

@Component({
  selector: 'gha-amortization-upload-detail',
  templateUrl: './amortization-upload-detail.component.html'
})
export class AmortizationUploadDetailComponent implements OnInit {
  amortizationUpload: IAmortizationUpload;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationUpload }) => {
      this.amortizationUpload = amortizationUpload;
    });
  }

  previousState() {
    window.history.back();
  }
}
