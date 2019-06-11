import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportingRoutingModule } from './reporting-routing.module';
import { AmortizationEntryReportingComponent } from './amortization-entry-reporting/amortization-entry-reporting.component';
import { PrepaymentEntryReportingComponent } from './prepayment-entry-reporting/prepayment-entry-reporting.component';
import { RegisteredSupplierReportingComponent } from './registered-supplier-reporting/registered-supplier-reporting.component';
import { ServiceOutletReportingComponent } from './service-outlet-reporting/service-outlet-reporting.component';
import { TransactionAccountReportingComponent } from './transaction-account-reporting/transaction-account-reporting.component';
import {HTTP_INTERCEPTORS } from '@angular/common/http';
import {NotificationInterceptor} from 'app/blocks/interceptor/notification.interceptor';
import {AuthExpiredInterceptor} from 'app/blocks/interceptor/auth-expired.interceptor';
import {ErrorHandlerInterceptor} from 'app/blocks/interceptor/errorhandler.interceptor';
import {PrepsSharedModule} from 'app/shared';

@NgModule({
    declarations: [AmortizationEntryReportingComponent, PrepaymentEntryReportingComponent, RegisteredSupplierReportingComponent, ServiceOutletReportingComponent, TransactionAccountReportingComponent],
    imports: [CommonModule, ReportingRoutingModule, PrepsSharedModule],
    exports: [AmortizationEntryReportingComponent, PrepaymentEntryReportingComponent, RegisteredSupplierReportingComponent, ServiceOutletReportingComponent, TransactionAccountReportingComponent],
    entryComponents: [AmortizationEntryReportingComponent, PrepaymentEntryReportingComponent, RegisteredSupplierReportingComponent, ServiceOutletReportingComponent, TransactionAccountReportingComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
})
export class ReportingModule {}
