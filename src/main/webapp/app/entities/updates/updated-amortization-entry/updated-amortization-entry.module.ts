import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  UpdatedAmortizationEntryComponent,
  UpdatedAmortizationEntryDetailComponent,
  UpdatedAmortizationEntryUpdateComponent,
  UpdatedAmortizationEntryDeletePopupComponent,
  UpdatedAmortizationEntryDeleteDialogComponent,
  updatedAmortizationEntryRoute,
  updatedAmortizationEntryPopupRoute
} from './';

const ENTITY_STATES = [...updatedAmortizationEntryRoute, ...updatedAmortizationEntryPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UpdatedAmortizationEntryComponent,
    UpdatedAmortizationEntryDetailComponent,
    UpdatedAmortizationEntryUpdateComponent,
    UpdatedAmortizationEntryDeleteDialogComponent,
    UpdatedAmortizationEntryDeletePopupComponent
  ],
  entryComponents: [
    UpdatedAmortizationEntryComponent,
    UpdatedAmortizationEntryUpdateComponent,
    UpdatedAmortizationEntryDeleteDialogComponent,
    UpdatedAmortizationEntryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsUpdatedAmortizationEntryModule {}
