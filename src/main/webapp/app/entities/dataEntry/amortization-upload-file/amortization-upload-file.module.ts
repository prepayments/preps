import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  AmortizationUploadFileComponent,
  AmortizationUploadFileDetailComponent,
  AmortizationUploadFileUpdateComponent,
  AmortizationUploadFileDeletePopupComponent,
  AmortizationUploadFileDeleteDialogComponent,
  amortizationUploadFileRoute,
  amortizationUploadFilePopupRoute
} from './';

const ENTITY_STATES = [...amortizationUploadFileRoute, ...amortizationUploadFilePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmortizationUploadFileComponent,
    AmortizationUploadFileDetailComponent,
    AmortizationUploadFileUpdateComponent,
    AmortizationUploadFileDeleteDialogComponent,
    AmortizationUploadFileDeletePopupComponent
  ],
  entryComponents: [
    AmortizationUploadFileComponent,
    AmortizationUploadFileUpdateComponent,
    AmortizationUploadFileDeleteDialogComponent,
    AmortizationUploadFileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAmortizationUploadFileModule {}
