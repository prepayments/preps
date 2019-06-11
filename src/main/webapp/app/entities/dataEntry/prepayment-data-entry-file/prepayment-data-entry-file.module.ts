import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  PrepaymentDataEntryFileComponent,
  PrepaymentDataEntryFileDetailComponent,
  PrepaymentDataEntryFileUpdateComponent,
  PrepaymentDataEntryFileDeletePopupComponent,
  PrepaymentDataEntryFileDeleteDialogComponent,
  prepaymentDataEntryFileRoute,
  prepaymentDataEntryFilePopupRoute
} from './';

const ENTITY_STATES = [...prepaymentDataEntryFileRoute, ...prepaymentDataEntryFilePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PrepaymentDataEntryFileComponent,
    PrepaymentDataEntryFileDetailComponent,
    PrepaymentDataEntryFileUpdateComponent,
    PrepaymentDataEntryFileDeleteDialogComponent,
    PrepaymentDataEntryFileDeletePopupComponent
  ],
  entryComponents: [
    PrepaymentDataEntryFileComponent,
    PrepaymentDataEntryFileUpdateComponent,
    PrepaymentDataEntryFileDeleteDialogComponent,
    PrepaymentDataEntryFileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsPrepaymentDataEntryFileModule {}
