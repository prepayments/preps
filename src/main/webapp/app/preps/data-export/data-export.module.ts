import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataExportRoutingModule } from './data-export-routing.module';
import { AmortizationEntriesComponent } from './amortization-entries/amortization-entries.component';
import { PrepaymentEntriesComponent } from './prepayment-entries/prepayment-entries.component';
import { ServiceOutletsComponent } from './service-outlets/service-outlets.component';
import { RegisteredSuppliersComponent } from './registered-suppliers/registered-suppliers.component';
import { TransactionAccountsComponent } from './transaction-accounts/transaction-accounts.component';
import { DataTablesModule } from 'angular-datatables';
import { PrepaymentBalanceComponent } from './prepayment-balance/prepayment-balance.component';

@NgModule({
  declarations: [
    AmortizationEntriesComponent,
    PrepaymentEntriesComponent,
    ServiceOutletsComponent,
    RegisteredSuppliersComponent,
    TransactionAccountsComponent,
    PrepaymentBalanceComponent
  ],
  imports: [CommonModule, DataExportRoutingModule, DataTablesModule],
  exports: [
    AmortizationEntriesComponent,
    PrepaymentEntriesComponent,
    ServiceOutletsComponent,
    RegisteredSuppliersComponent,
    TransactionAccountsComponent,
    PrepaymentBalanceComponent
  ],
  entryComponents: [
    AmortizationEntriesComponent,
    PrepaymentEntriesComponent,
    ServiceOutletsComponent,
    RegisteredSuppliersComponent,
    TransactionAccountsComponent,
    PrepaymentBalanceComponent
  ]
})
export class DataExportModule {}
