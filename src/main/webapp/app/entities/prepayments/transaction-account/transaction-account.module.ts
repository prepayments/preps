import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  TransactionAccountComponent,
  TransactionAccountDetailComponent,
  TransactionAccountUpdateComponent,
  TransactionAccountDeletePopupComponent,
  TransactionAccountDeleteDialogComponent,
  transactionAccountRoute,
  transactionAccountPopupRoute
} from './';

const ENTITY_STATES = [...transactionAccountRoute, ...transactionAccountPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionAccountComponent,
    TransactionAccountDetailComponent,
    TransactionAccountUpdateComponent,
    TransactionAccountDeleteDialogComponent,
    TransactionAccountDeletePopupComponent
  ],
  entryComponents: [
    TransactionAccountComponent,
    TransactionAccountUpdateComponent,
    TransactionAccountDeleteDialogComponent,
    TransactionAccountDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsTransactionAccountModule {}
