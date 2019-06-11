import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';

@Component({
  selector: 'gha-registered-supplier-detail',
  templateUrl: './registered-supplier-detail.component.html'
})
export class RegisteredSupplierDetailComponent implements OnInit {
  registeredSupplier: IRegisteredSupplier;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ registeredSupplier }) => {
      this.registeredSupplier = registeredSupplier;
    });
  }

  previousState() {
    window.history.back();
  }
}
