import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  AmortizationEntryComponent,
  AmortizationEntryDetailComponent,
  AmortizationEntryUpdateComponent,
  AmortizationEntryDeletePopupComponent,
  AmortizationEntryDeleteDialogComponent,
  amortizationEntryRoute,
  amortizationEntryPopupRoute
} from './';

const ENTITY_STATES = [...amortizationEntryRoute, ...amortizationEntryPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmortizationEntryComponent,
    AmortizationEntryDetailComponent,
    AmortizationEntryUpdateComponent,
    AmortizationEntryDeleteDialogComponent,
    AmortizationEntryDeletePopupComponent
  ],
  entryComponents: [
    AmortizationEntryComponent,
    AmortizationEntryUpdateComponent,
    AmortizationEntryDeleteDialogComponent,
    AmortizationEntryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAmortizationEntryModule {}
