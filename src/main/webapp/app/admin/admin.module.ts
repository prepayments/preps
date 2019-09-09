import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PrepsSharedModule } from 'app/shared';
import {
  adminState,
  AuditsComponent,
  GhaConfigurationComponent,
  GhaDocsComponent,
  GhaHealthCheckComponent,
  GhaHealthModalComponent,
  GhaMetricsMonitoringComponent,
  GhaTrackerComponent,
  LogsComponent,
  UserMgmtComponent,
  UserMgmtDeleteDialogComponent,
  UserMgmtDetailComponent,
  UserMgmtUpdateComponent
} from './';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    PrepsSharedModule,
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild(adminState)
  ],
  declarations: [
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    GhaConfigurationComponent,
    GhaHealthCheckComponent,
    GhaHealthModalComponent,
    GhaDocsComponent,
    GhaTrackerComponent,
    GhaMetricsMonitoringComponent
  ],
  exports: [UserMgmtComponent, GhaMetricsMonitoringComponent, GhaHealthCheckComponent, GhaHealthModalComponent, GhaTrackerComponent],
  entryComponents: [
    UserMgmtDeleteDialogComponent,
    GhaHealthModalComponent,
    GhaHealthModalComponent,
    GhaTrackerComponent,
    UserMgmtComponent,
    GhaMetricsMonitoringComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsAdminModule {}
