import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  TransactionAccountDataEntryFileComponent,
  TransactionAccountDataEntryFileDetailComponent,
  TransactionAccountDataEntryFileUpdateComponent,
  TransactionAccountDataEntryFileDeletePopupComponent,
  TransactionAccountDataEntryFileDeleteDialogComponent,
  transactionAccountDataEntryFileRoute,
  transactionAccountDataEntryFilePopupRoute
} from './';

const ENTITY_STATES = [...transactionAccountDataEntryFileRoute, ...transactionAccountDataEntryFilePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionAccountDataEntryFileComponent,
    TransactionAccountDataEntryFileDetailComponent,
    TransactionAccountDataEntryFileUpdateComponent,
    TransactionAccountDataEntryFileDeleteDialogComponent,
    TransactionAccountDataEntryFileDeletePopupComponent
  ],
  entryComponents: [
    TransactionAccountDataEntryFileComponent,
    TransactionAccountDataEntryFileUpdateComponent,
    TransactionAccountDataEntryFileDeleteDialogComponent,
    TransactionAccountDataEntryFileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsTransactionAccountDataEntryFileModule {}
