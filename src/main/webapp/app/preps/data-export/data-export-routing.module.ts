import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AmortizationEntriesComponent } from './amortization-entries/amortization-entries.component';
import { PrepaymentEntriesComponent } from './prepayment-entries/prepayment-entries.component';
import { RegisteredSuppliersComponent } from './registered-suppliers/registered-suppliers.component';
import { ServiceOutletsComponent } from './service-outlets/service-outlets.component';
import { TransactionAccountsComponent } from './transaction-accounts/transaction-accounts.component';
import {PrepaymentsReportResolverService} from 'app/preps/data-export/prepayment-entries/prepayments-report-resolver.service';

const routes: Routes = [
    {
        path: 'amortization-entries',
        component: AmortizationEntriesComponent,
        data: {
            pageTitle: 'Export | Amortization Entries',
            authorities: ['ROLE_USER']
        }
    },
    {
        path: 'prepayment-entries',
        component: PrepaymentEntriesComponent,
        resolve: {
            prepaymentsResolved: PrepaymentsReportResolverService
        },
        data: {
            pageTitle: 'Export | Prepayment Entries'
        }
    },
    {
        path: 'registered-suppliers',
        component: RegisteredSuppliersComponent,
        data: {
            pageTitle: 'Export | Registered Suppliers'
        }
    },
    {
        path: 'service-outlets',
        component: ServiceOutletsComponent,
        data: {
            pageTitle: 'Export | Service Outlets'
        }
    },
    {
        path: 'transaction-accounts',
        component: TransactionAccountsComponent,
        data: {
            pageTitle: 'Export | Transaction Accounts'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DataExportRoutingModule {}
