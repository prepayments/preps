import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  ReportRequestEventComponent,
  ReportRequestEventDetailComponent,
  ReportRequestEventUpdateComponent,
  ReportRequestEventDeletePopupComponent,
  ReportRequestEventDeleteDialogComponent,
  reportRequestEventRoute,
  reportRequestEventPopupRoute
} from './';

const ENTITY_STATES = [...reportRequestEventRoute, ...reportRequestEventPopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReportRequestEventComponent,
    ReportRequestEventDetailComponent,
    ReportRequestEventUpdateComponent,
    ReportRequestEventDeleteDialogComponent,
    ReportRequestEventDeletePopupComponent
  ],
  entryComponents: [
    ReportRequestEventComponent,
    ReportRequestEventUpdateComponent,
    ReportRequestEventDeleteDialogComponent,
    ReportRequestEventDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsReportRequestEventModule {}
