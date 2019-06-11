import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  AmortizationDataEntryFileComponent,
  AmortizationDataEntryFileDetailComponent,
  AmortizationDataEntryFileUpdateComponent,
  AmortizationDataEntryFileDeletePopupComponent,
  AmortizationDataEntryFileDeleteDialogComponent,
  amortizationDataEntryFileRoute,
  amortizationDataEntryFilePopupRoute
} from './';

const ENTITY_STATES = [...amortizationDataEntryFileRoute, ...amortizationDataEntryFilePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmortizationDataEntryFileComponent,
    AmortizationDataEntryFileDetailComponent,
    AmortizationDataEntryFileUpdateComponent,
    AmortizationDataEntryFileDeleteDialogComponent,
    AmortizationDataEntryFileDeletePopupComponent
  ],
  entryComponents: [
    AmortizationDataEntryFileComponent,
    AmortizationDataEntryFileUpdateComponent,
    AmortizationDataEntryFileDeleteDialogComponent,
    AmortizationDataEntryFileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAmortizationDataEntryFileModule {}
