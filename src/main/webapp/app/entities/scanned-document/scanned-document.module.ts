import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  ScannedDocumentComponent,
  ScannedDocumentDetailComponent,
  ScannedDocumentUpdateComponent,
  ScannedDocumentDeletePopupComponent,
  ScannedDocumentDeleteDialogComponent,
  scannedDocumentRoute,
  scannedDocumentPopupRoute
} from './';

const ENTITY_STATES = [...scannedDocumentRoute, ...scannedDocumentPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ScannedDocumentComponent,
    ScannedDocumentDetailComponent,
    ScannedDocumentUpdateComponent,
    ScannedDocumentDeleteDialogComponent,
    ScannedDocumentDeletePopupComponent
  ],
  entryComponents: [
    ScannedDocumentComponent,
    ScannedDocumentUpdateComponent,
    ScannedDocumentDeleteDialogComponent,
    ScannedDocumentDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsScannedDocumentModule {}
