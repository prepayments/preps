import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  AmortizationEntryUpdateComponent,
  AmortizationEntryUpdateDetailComponent,
  AmortizationEntryUpdateUpdateComponent,
  AmortizationEntryUpdateDeletePopupComponent,
  AmortizationEntryUpdateDeleteDialogComponent,
  amortizationEntryUpdateRoute,
  amortizationEntryUpdatePopupRoute
} from './';

const ENTITY_STATES = [...amortizationEntryUpdateRoute, ...amortizationEntryUpdatePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmortizationEntryUpdateComponent,
    AmortizationEntryUpdateDetailComponent,
    AmortizationEntryUpdateUpdateComponent,
    AmortizationEntryUpdateDeleteDialogComponent,
    AmortizationEntryUpdateDeletePopupComponent
  ],
  entryComponents: [
    AmortizationEntryUpdateComponent,
    AmortizationEntryUpdateUpdateComponent,
    AmortizationEntryUpdateDeleteDialogComponent,
    AmortizationEntryUpdateDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAmortizationEntryUpdateModule {}
