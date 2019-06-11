import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  AccountingTransactionComponent,
  AccountingTransactionDetailComponent,
  AccountingTransactionUpdateComponent,
  AccountingTransactionDeletePopupComponent,
  AccountingTransactionDeleteDialogComponent,
  accountingTransactionRoute,
  accountingTransactionPopupRoute
} from './';

const ENTITY_STATES = [...accountingTransactionRoute, ...accountingTransactionPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountingTransactionComponent,
    AccountingTransactionDetailComponent,
    AccountingTransactionUpdateComponent,
    AccountingTransactionDeleteDialogComponent,
    AccountingTransactionDeletePopupComponent
  ],
  entryComponents: [
    AccountingTransactionComponent,
    AccountingTransactionUpdateComponent,
    AccountingTransactionDeleteDialogComponent,
    AccountingTransactionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAccountingTransactionModule {}
