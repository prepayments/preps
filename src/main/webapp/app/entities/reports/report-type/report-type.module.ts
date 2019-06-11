import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrepsSharedModule } from 'app/shared';
import {
  ReportTypeComponent,
  ReportTypeDetailComponent,
  ReportTypeUpdateComponent,
  ReportTypeDeletePopupComponent,
  ReportTypeDeleteDialogComponent,
  reportTypeRoute,
  reportTypePopupRoute
} from './';

const ENTITY_STATES = [...reportTypeRoute, ...reportTypePopupRoute];

@NgModule({
  imports: [PrepsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReportTypeComponent,
    ReportTypeDetailComponent,
    ReportTypeUpdateComponent,
    ReportTypeDeleteDialogComponent,
    ReportTypeDeletePopupComponent
  ],
  entryComponents: [ReportTypeComponent, ReportTypeUpdateComponent, ReportTypeDeleteDialogComponent, ReportTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsReportTypeModule {}
