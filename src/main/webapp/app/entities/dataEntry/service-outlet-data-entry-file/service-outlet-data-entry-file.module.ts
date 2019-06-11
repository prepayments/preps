import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  ServiceOutletDataEntryFileComponent,
  ServiceOutletDataEntryFileDetailComponent,
  ServiceOutletDataEntryFileUpdateComponent,
  ServiceOutletDataEntryFileDeletePopupComponent,
  ServiceOutletDataEntryFileDeleteDialogComponent,
  serviceOutletDataEntryFileRoute,
  serviceOutletDataEntryFilePopupRoute
} from './';

const ENTITY_STATES = [...serviceOutletDataEntryFileRoute, ...serviceOutletDataEntryFilePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceOutletDataEntryFileComponent,
    ServiceOutletDataEntryFileDetailComponent,
    ServiceOutletDataEntryFileUpdateComponent,
    ServiceOutletDataEntryFileDeleteDialogComponent,
    ServiceOutletDataEntryFileDeletePopupComponent
  ],
  entryComponents: [
    ServiceOutletDataEntryFileComponent,
    ServiceOutletDataEntryFileUpdateComponent,
    ServiceOutletDataEntryFileDeleteDialogComponent,
    ServiceOutletDataEntryFileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsServiceOutletDataEntryFileModule {}
