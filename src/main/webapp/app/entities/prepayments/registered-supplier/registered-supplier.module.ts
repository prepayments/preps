import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  RegisteredSupplierComponent,
  RegisteredSupplierDetailComponent,
  RegisteredSupplierUpdateComponent,
  RegisteredSupplierDeletePopupComponent,
  RegisteredSupplierDeleteDialogComponent,
  registeredSupplierRoute,
  registeredSupplierPopupRoute
} from './';

const ENTITY_STATES = [...registeredSupplierRoute, ...registeredSupplierPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RegisteredSupplierComponent,
    RegisteredSupplierDetailComponent,
    RegisteredSupplierUpdateComponent,
    RegisteredSupplierDeleteDialogComponent,
    RegisteredSupplierDeletePopupComponent
  ],
  entryComponents: [
    RegisteredSupplierComponent,
    RegisteredSupplierUpdateComponent,
    RegisteredSupplierDeleteDialogComponent,
    RegisteredSupplierDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsRegisteredSupplierModule {}
