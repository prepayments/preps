import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  AmortizationUploadComponent,
  AmortizationUploadDetailComponent,
  AmortizationUploadUpdateComponent,
  AmortizationUploadDeletePopupComponent,
  AmortizationUploadDeleteDialogComponent,
  amortizationUploadRoute,
  amortizationUploadPopupRoute
} from './';

const ENTITY_STATES = [...amortizationUploadRoute, ...amortizationUploadPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmortizationUploadComponent,
    AmortizationUploadDetailComponent,
    AmortizationUploadUpdateComponent,
    AmortizationUploadDeleteDialogComponent,
    AmortizationUploadDeletePopupComponent
  ],
  entryComponents: [
    AmortizationUploadComponent,
    AmortizationUploadUpdateComponent,
    AmortizationUploadDeleteDialogComponent,
    AmortizationUploadDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAmortizationUploadModule {}
