import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  AmortizationUpdateFileComponent,
  AmortizationUpdateFileDetailComponent,
  AmortizationUpdateFileUpdateComponent,
  AmortizationUpdateFileDeletePopupComponent,
  AmortizationUpdateFileDeleteDialogComponent,
  amortizationUpdateFileRoute,
  amortizationUpdateFilePopupRoute
} from './';

const ENTITY_STATES = [...amortizationUpdateFileRoute, ...amortizationUpdateFilePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmortizationUpdateFileComponent,
    AmortizationUpdateFileDetailComponent,
    AmortizationUpdateFileUpdateComponent,
    AmortizationUpdateFileDeleteDialogComponent,
    AmortizationUpdateFileDeletePopupComponent
  ],
  entryComponents: [
    AmortizationUpdateFileComponent,
    AmortizationUpdateFileUpdateComponent,
    AmortizationUpdateFileDeleteDialogComponent,
    AmortizationUpdateFileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAmortizationUpdateFileModule {}
