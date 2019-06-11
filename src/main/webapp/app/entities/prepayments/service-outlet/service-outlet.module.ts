import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  ServiceOutletComponent,
  ServiceOutletDetailComponent,
  ServiceOutletUpdateComponent,
  ServiceOutletDeletePopupComponent,
  ServiceOutletDeleteDialogComponent,
  serviceOutletRoute,
  serviceOutletPopupRoute
} from './';

const ENTITY_STATES = [...serviceOutletRoute, ...serviceOutletPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceOutletComponent,
    ServiceOutletDetailComponent,
    ServiceOutletUpdateComponent,
    ServiceOutletDeleteDialogComponent,
    ServiceOutletDeletePopupComponent
  ],
  entryComponents: [
    ServiceOutletComponent,
    ServiceOutletUpdateComponent,
    ServiceOutletDeleteDialogComponent,
    ServiceOutletDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsServiceOutletModule {}
