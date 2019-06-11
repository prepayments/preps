import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  SupplierDataEntryFileComponent,
  SupplierDataEntryFileDetailComponent,
  SupplierDataEntryFileUpdateComponent,
  SupplierDataEntryFileDeletePopupComponent,
  SupplierDataEntryFileDeleteDialogComponent,
  supplierDataEntryFileRoute,
  supplierDataEntryFilePopupRoute
} from './';

const ENTITY_STATES = [...supplierDataEntryFileRoute, ...supplierDataEntryFilePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SupplierDataEntryFileComponent,
    SupplierDataEntryFileDetailComponent,
    SupplierDataEntryFileUpdateComponent,
    SupplierDataEntryFileDeleteDialogComponent,
    SupplierDataEntryFileDeletePopupComponent
  ],
  entryComponents: [
    SupplierDataEntryFileComponent,
    SupplierDataEntryFileUpdateComponent,
    SupplierDataEntryFileDeleteDialogComponent,
    SupplierDataEntryFileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsSupplierDataEntryFileModule {}
