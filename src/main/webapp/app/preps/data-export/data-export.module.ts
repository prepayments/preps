import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataExportRoutingModule } from './data-export-routing.module';
import { AmortizationEntriesComponent } from './amortization-entries/amortization-entries.component';
import { PrepaymentEntriesComponent } from './prepayment-entries/prepayment-entries.component';
import { ServiceOutletsComponent } from './service-outlets/service-outlets.component';
import { RegisteredSuppliersComponent } from './registered-suppliers/registered-suppliers.component';
import { TransactionAccountsComponent } from './transaction-accounts/transaction-accounts.component';
import { DataTablesModule } from 'angular-datatables';

@NgModule({
    declarations: [
        AmortizationEntriesComponent,
        PrepaymentEntriesComponent,
        ServiceOutletsComponent,
        RegisteredSuppliersComponent,
        TransactionAccountsComponent
    ],
    imports: [CommonModule, DataExportRoutingModule, DataTablesModule],
    exports: [
        AmortizationEntriesComponent,
        PrepaymentEntriesComponent,
        ServiceOutletsComponent,
        RegisteredSuppliersComponent,
        TransactionAccountsComponent
    ],
    entryComponents: [
        AmortizationEntriesComponent,
        PrepaymentEntriesComponent,
        ServiceOutletsComponent,
        RegisteredSuppliersComponent,
        TransactionAccountsComponent
    ]
})
export class DataExportModule {}
