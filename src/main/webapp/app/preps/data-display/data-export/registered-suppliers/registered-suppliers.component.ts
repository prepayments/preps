import { Component, OnDestroy, OnInit } from '@angular/core';
import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { Subject } from 'rxjs/internal/Subject';
import { HttpResponse } from '@angular/common/http';
import { NGXLogger } from 'ngx-logger';
import { JhiAlertService } from 'ng-jhipster';
import { RegisteredSuppliersReportingService } from 'app/preps/data-display/data-export/registered-suppliers/registered-suppliers-reporting.service';

@Component({
  selector: 'gha-registered-suppliers',
  templateUrl: './registered-suppliers.component.html',
  styleUrls: ['./registered-suppliers.component.scss']
})
export class RegisteredSuppliersComponent implements OnInit, OnDestroy {
  registeredSuppliers: IRegisteredSupplier[];

  dtOptions: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();

  constructor(
    private registeredSuppliersReportingService: RegisteredSuppliersReportingService,
    private jhiAlertService: JhiAlertService,
    private log: NGXLogger
  ) {
    this.registeredSuppliersReportingService.getEntities().subscribe(
      (data: HttpResponse<IRegisteredSupplier[]>) => {
        this.registeredSuppliers = data.body;
        this.dtTrigger.next();
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.registeredSuppliers.length} prepayment entities`)
    );

    this.dtOptions = {
      searching: true,
      paging: true,
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      dom: 'Bfrtip',
      buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
    };
  }

  ngOnInit() {
    this.registeredSuppliersReportingService.getEntities().subscribe(
      (data: HttpResponse<IRegisteredSupplier[]>) => {
        this.registeredSuppliers = data.body;
        this.dtTrigger.next();
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.registeredSuppliers.length} prepayment entities`)
    );
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
    this.registeredSuppliers = [];
  }

  protected onError(errorMessage: string) {
    // let user know
    this.jhiAlertService.error(errorMessage, null, null);
    // push to console
    this.log.error(`Error while extracting suppliers from API ${errorMessage}`);
  }
}
