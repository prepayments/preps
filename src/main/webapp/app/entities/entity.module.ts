import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'amortization-entry',
        loadChildren: './prepayments/amortization-entry/amortization-entry.module#PrepsAmortizationEntryModule'
      },
      {
        path: 'prepayment-entry',
        loadChildren: './prepayments/prepayment-entry/prepayment-entry.module#PrepsPrepaymentEntryModule'
      },
      {
        path: 'accounting-transaction',
        loadChildren: './prepayments/accounting-transaction/accounting-transaction.module#PrepsAccountingTransactionModule'
      },
      {
        path: 'service-outlet',
        loadChildren: './prepayments/service-outlet/service-outlet.module#PrepsServiceOutletModule'
      },
      {
        path: 'registered-supplier',
        loadChildren: './prepayments/registered-supplier/registered-supplier.module#PrepsRegisteredSupplierModule'
      },
      {
        path: 'amortization-data-entry-file',
        loadChildren: './dataEntry/amortization-data-entry-file/amortization-data-entry-file.module#PrepsAmortizationDataEntryFileModule'
      },
      {
        path: 'prepayment-data-entry-file',
        loadChildren: './dataEntry/prepayment-data-entry-file/prepayment-data-entry-file.module#PrepsPrepaymentDataEntryFileModule'
      },
      {
        path: 'supplier-data-entry-file',
        loadChildren: './dataEntry/supplier-data-entry-file/supplier-data-entry-file.module#PrepsSupplierDataEntryFileModule'
      },
      {
        path: 'service-outlet-data-entry-file',
        loadChildren:
          './dataEntry/service-outlet-data-entry-file/service-outlet-data-entry-file.module#PrepsServiceOutletDataEntryFileModule'
      },
      {
        path: 'transaction-account-data-entry-file',
        loadChildren:
          './dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file.module#PrepsTransactionAccountDataEntryFileModule'
      },
      {
        path: 'report-type',
        loadChildren: './reports/report-type/report-type.module#PrepsReportTypeModule'
      },
      {
        path: 'report-request-event',
        loadChildren: './reports/report-request-event/report-request-event.module#PrepsReportRequestEventModule'
      },
      {
        path: 'transaction-account',
        loadChildren: './prepayments/transaction-account/transaction-account.module#PrepsTransactionAccountModule'
      },
      {
        path: 'accounting-transaction',
        loadChildren: './prepayments/accounting-transaction/accounting-transaction.module#PrepsAccountingTransactionModule'
      },
      {
        path: 'amortization-entry',
        loadChildren: './prepayments/amortization-entry/amortization-entry.module#PrepsAmortizationEntryModule'
      },
      {
        path: 'prepayment-entry',
        loadChildren: './prepayments/prepayment-entry/prepayment-entry.module#PrepsPrepaymentEntryModule'
      },
      {
        path: 'registered-supplier',
        loadChildren: './prepayments/registered-supplier/registered-supplier.module#PrepsRegisteredSupplierModule'
      },
      {
        path: 'report-request-event',
        loadChildren: './reports/report-request-event/report-request-event.module#PrepsReportRequestEventModule'
      },
      {
        path: 'service-outlet',
        loadChildren: './prepayments/service-outlet/service-outlet.module#PrepsServiceOutletModule'
      },
      {
        path: 'transaction-account',
        loadChildren: './prepayments/transaction-account/transaction-account.module#PrepsTransactionAccountModule'
      },
      {
        path: 'scanned-document',
        loadChildren: './scanned-document/scanned-document.module#PrepsScannedDocumentModule'
      },
      {
        path: 'scanned-document',
        loadChildren: './scanned-document/scanned-document.module#PrepsScannedDocumentModule'
      },
      {
        path: 'scanned-document',
        loadChildren: './scanned-document/scanned-document.module#PrepsScannedDocumentModule'
      },
      {
        path: 'scanned-document',
        loadChildren: './scanned-document/scanned-document.module#PrepsScannedDocumentModule'
      },
      {
        path: 'prepayment-entry',
        loadChildren: './prepayments/prepayment-entry/prepayment-entry.module#PrepsPrepaymentEntryModule'
      },
      {
        path: 'amortization-upload',
        loadChildren: './dataEntry/amortization-upload/amortization-upload.module#PrepsAmortizationUploadModule'
      },
      {
        path: 'amortization-upload-file',
        loadChildren: './dataEntry/amortization-upload-file/amortization-upload-file.module#PrepsAmortizationUploadFileModule'
      },
      {
        path: 'accounting-transaction',
        loadChildren: './prepayments/accounting-transaction/accounting-transaction.module#PrepsAccountingTransactionModule'
      },
      {
        path: 'transaction-account',
        loadChildren: './prepayments/transaction-account/transaction-account.module#PrepsTransactionAccountModule'
      },
      {
        path: 'amortization-entry',
        loadChildren: './prepayments/amortization-entry/amortization-entry.module#PrepsAmortizationEntryModule'
      },
      {
        path: 'amortization-upload',
        loadChildren: './dataEntry/amortization-upload/amortization-upload.module#PrepsAmortizationUploadModule'
      },
      {
        path: 'prepayment-entry',
        loadChildren: './prepayments/prepayment-entry/prepayment-entry.module#PrepsPrepaymentEntryModule'
      },
      {
        path: 'registered-supplier',
        loadChildren: './prepayments/registered-supplier/registered-supplier.module#PrepsRegisteredSupplierModule'
      },
      {
        path: 'service-outlet',
        loadChildren: './prepayments/service-outlet/service-outlet.module#PrepsServiceOutletModule'
      },
      {
        path: 'transaction-account',
        loadChildren: './prepayments/transaction-account/transaction-account.module#PrepsTransactionAccountModule'
      },
      {
        path: 'transaction-account',
        loadChildren: './prepayments/transaction-account/transaction-account.module#PrepsTransactionAccountModule'
      },
      {
        path: 'transaction-account',
        loadChildren: './prepayments/transaction-account/transaction-account.module#PrepsTransactionAccountModule'
      },
      {
        path: 'amortization-data-entry-file',
        loadChildren: './dataEntry/amortization-data-entry-file/amortization-data-entry-file.module#PrepsAmortizationDataEntryFileModule'
      },
      {
        path: 'amortization-upload-file',
        loadChildren: './dataEntry/amortization-upload-file/amortization-upload-file.module#PrepsAmortizationUploadFileModule'
      },
      {
        path: 'prepayment-data-entry-file',
        loadChildren: './dataEntry/prepayment-data-entry-file/prepayment-data-entry-file.module#PrepsPrepaymentDataEntryFileModule'
      },
      {
        path: 'service-outlet-data-entry-file',
        loadChildren:
          './dataEntry/service-outlet-data-entry-file/service-outlet-data-entry-file.module#PrepsServiceOutletDataEntryFileModule'
      },
      {
        path: 'supplier-data-entry-file',
        loadChildren: './dataEntry/supplier-data-entry-file/supplier-data-entry-file.module#PrepsSupplierDataEntryFileModule'
      },
      {
        path: 'transaction-account-data-entry-file',
        loadChildren:
          './dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file.module#PrepsTransactionAccountDataEntryFileModule'
      },
      {
        path: 'amortization-data-entry-file',
        loadChildren: './dataEntry/amortization-data-entry-file/amortization-data-entry-file.module#PrepsAmortizationDataEntryFileModule'
      },
      {
        path: 'amortization-upload-file',
        loadChildren: './dataEntry/amortization-upload-file/amortization-upload-file.module#PrepsAmortizationUploadFileModule'
      },
      {
        path: 'prepayment-data-entry-file',
        loadChildren: './dataEntry/prepayment-data-entry-file/prepayment-data-entry-file.module#PrepsPrepaymentDataEntryFileModule'
      },
      {
        path: 'service-outlet-data-entry-file',
        loadChildren:
          './dataEntry/service-outlet-data-entry-file/service-outlet-data-entry-file.module#PrepsServiceOutletDataEntryFileModule'
      },
      {
        path: 'supplier-data-entry-file',
        loadChildren: './dataEntry/supplier-data-entry-file/supplier-data-entry-file.module#PrepsSupplierDataEntryFileModule'
      },
      {
        path: 'transaction-account-data-entry-file',
        loadChildren:
          './dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file.module#PrepsTransactionAccountDataEntryFileModule'
      },
      {
        path: 'registered-supplier',
        loadChildren: './prepayments/registered-supplier/registered-supplier.module#PrepsRegisteredSupplierModule'
      },
      {
        path: 'registered-supplier',
        loadChildren: './prepayments/registered-supplier/registered-supplier.module#PrepsRegisteredSupplierModule'
      },
      {
        path: 'amortization-entry',
        loadChildren: './prepayments/amortization-entry/amortization-entry.module#PrepsAmortizationEntryModule'
      },
      {
        path: 'amortization-upload',
        loadChildren: './dataEntry/amortization-upload/amortization-upload.module#PrepsAmortizationUploadModule'
      },
      {
        path: 'amortization-entry',
        loadChildren: './prepayments/amortization-entry/amortization-entry.module#PrepsAmortizationEntryModule'
      },
      {
        path: 'amortization-data-entry-file',
        loadChildren: './dataEntry/amortization-data-entry-file/amortization-data-entry-file.module#PrepsAmortizationDataEntryFileModule'
      },
      {
        path: 'amortization-upload-file',
        loadChildren: './dataEntry/amortization-upload-file/amortization-upload-file.module#PrepsAmortizationUploadFileModule'
      },
      {
        path: 'prepayment-data-entry-file',
        loadChildren: './dataEntry/prepayment-data-entry-file/prepayment-data-entry-file.module#PrepsPrepaymentDataEntryFileModule'
      },
      {
        path: 'service-outlet-data-entry-file',
        loadChildren:
          './dataEntry/service-outlet-data-entry-file/service-outlet-data-entry-file.module#PrepsServiceOutletDataEntryFileModule'
      },
      {
        path: 'supplier-data-entry-file',
        loadChildren: './dataEntry/supplier-data-entry-file/supplier-data-entry-file.module#PrepsSupplierDataEntryFileModule'
      },
      {
        path: 'transaction-account-data-entry-file',
        loadChildren:
          './dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file.module#PrepsTransactionAccountDataEntryFileModule'
      },
      {
        path: 'amortization-upload',
        loadChildren: './dataEntry/amortization-upload/amortization-upload.module#PrepsAmortizationUploadModule'
      },
      {
        path: 'amortization-upload',
        loadChildren: './dataEntry/amortization-upload/amortization-upload.module#PrepsAmortizationUploadModule'
      },
      {
        path: 'amortization-entry',
        loadChildren: './prepayments/amortization-entry/amortization-entry.module#PrepsAmortizationEntryModule'
      },
      {
        path: 'amortization-upload',
        loadChildren: './dataEntry/amortization-upload/amortization-upload.module#PrepsAmortizationUploadModule'
      },
      {
        path: 'prepayment-entry',
        loadChildren: './prepayments/prepayment-entry/prepayment-entry.module#PrepsPrepaymentEntryModule'
      },
      {
        path: 'accounting-transaction',
        loadChildren: './prepayments/accounting-transaction/accounting-transaction.module#PrepsAccountingTransactionModule'
      },
      {
        path: 'amortization-entry',
        loadChildren: './prepayments/amortization-entry/amortization-entry.module#PrepsAmortizationEntryModule'
      },
      {
        path: 'prepayment-entry',
        loadChildren: './prepayments/prepayment-entry/prepayment-entry.module#PrepsPrepaymentEntryModule'
      },
      {
        path: 'service-outlet',
        loadChildren: './prepayments/service-outlet/service-outlet.module#PrepsServiceOutletModule'
      },
      {
        path: 'accounting-transaction',
        loadChildren: './prepayments/accounting-transaction/accounting-transaction.module#PrepsAccountingTransactionModule'
      },
      {
        path: 'prepayment-entry',
        loadChildren: './prepayments/prepayment-entry/prepayment-entry.module#PrepsPrepaymentEntryModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsEntityModule {}
