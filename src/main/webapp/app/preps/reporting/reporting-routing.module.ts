import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AmortizationEntryReportingComponent} from 'app/preps/reporting/amortization-entry-reporting/amortization-entry-reporting.component';
import {PrepaymentEntryReportingComponent} from 'app/preps/reporting/prepayment-entry-reporting/prepayment-entry-reporting.component';
import {RegisteredSupplierReportingComponent} from 'app/preps/reporting/registered-supplier-reporting/registered-supplier-reporting.component';
import {ServiceOutletReportingComponent} from 'app/preps/reporting/service-outlet-reporting/service-outlet-reporting.component';
import {TransactionAccountReportingComponent} from 'app/preps/reporting/transaction-account-reporting/transaction-account-reporting.component';
import {AmortizationEntryResolveService} from 'app/preps/reporting/amortization-entry-reporting/amortization-entry-resolve.service';
import {PrepaymentEntryResolveService} from 'app/preps/reporting/prepayment-entry-reporting/prepayment-entry-resolve.service';
import {RegisteredSupplierReportResolveService} from 'app/preps/reporting/registered-supplier-reporting/registered-supplier-report-resolve.service';
import {ServiceOutletReportResolveService} from 'app/preps/reporting/service-outlet-reporting/service-outlet-report-resolve.service';
import {TransactionAccountsReportResolveService} from 'app/preps/reporting/transaction-account-reporting/transaction-accounts-report-resolve.service';

const routes: Routes = [
    {
        path: 'amortization-entries',
        component: AmortizationEntryReportingComponent,
        resolve: {
            amortizationEntryReport: AmortizationEntryResolveService
        },
        data: {
            pageTitle: 'Reporting Amortization Entries Listing'
        }
    },
    {
        path: 'prepayment-entries',
        component: PrepaymentEntryReportingComponent,
        resolve: {
            prepaymentEntryReport: PrepaymentEntryResolveService
        },
        data: {
            pageTitle: 'Reporting Prepayment Entries Listing'
        }
    },
    {
        path: 'registered-suppliers',
        component: RegisteredSupplierReportingComponent,
        resolve: {
            registeredSupplierReport: RegisteredSupplierReportResolveService
        },
        data: {
            pageTitle: 'Reporting Registered Suppliers Listing'
        }
    },
    {
        path: 'service-outlets',
        component: ServiceOutletReportingComponent,
        resolve: {
            serviceOutletReport: ServiceOutletReportResolveService
        },
        data: {
            pageTitle: 'Reporting Service Outlets Listing'
        }
    },
    {
        path: 'transaction-accounts',
        component: TransactionAccountReportingComponent,
        resolve: {
            transactionAccountReport: TransactionAccountsReportResolveService
        },
        data: {
            pageTitle: 'Reporting Transaction Accounts Listing'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ReportingRoutingModule {}
