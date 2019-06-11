import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  PrepaymentEntryComponent,
  PrepaymentEntryDetailComponent,
  PrepaymentEntryUpdateComponent,
  PrepaymentEntryDeletePopupComponent,
  PrepaymentEntryDeleteDialogComponent,
  prepaymentEntryRoute,
  prepaymentEntryPopupRoute
} from './';

const ENTITY_STATES = [...prepaymentEntryRoute, ...prepaymentEntryPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PrepaymentEntryComponent,
    PrepaymentEntryDetailComponent,
    PrepaymentEntryUpdateComponent,
    PrepaymentEntryDeleteDialogComponent,
    PrepaymentEntryDeletePopupComponent
  ],
  entryComponents: [
    PrepaymentEntryComponent,
    PrepaymentEntryUpdateComponent,
    PrepaymentEntryDeleteDialogComponent,
    PrepaymentEntryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsPrepaymentEntryModule {}
