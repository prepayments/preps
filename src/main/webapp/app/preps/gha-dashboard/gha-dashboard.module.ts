import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaDashboardRoutingModule } from './gha-dashboard-routing.module';
import { PrepsSharedModule } from 'app/shared';
import { DashboardContainerComponent } from './dashboard/dashboard-container.component';
import { UsersDashboardComponent } from './users-dashboard/users-dashboard.component';
import { PrepsAdminModule } from 'app/admin/admin.module';
import { adminState } from 'app/admin';
import { RouterModule } from '@angular/router';
import { MetricDashboardComponent } from './metric-dashboard/metric-dashboard.component';
import { PrepsMaterialModule } from 'app/preps/preps-material.module';
import { HealthDashboardComponent } from './health-dashboard/health-dashboard.component';
import { PrepaymentsDashboardComponent } from './prepayments-dashboard/prepayments-dashboard.component';
import { PrepsPrepaymentEntryModule } from 'app/entities/prepayments/prepayment-entry/prepayment-entry.module';
import { DataExportModule } from 'app/preps/data-export/data-export.module';

@NgModule({
  declarations: [
    DashboardContainerComponent,
    UsersDashboardComponent,
    MetricDashboardComponent,
    HealthDashboardComponent,
    PrepaymentsDashboardComponent
  ],
  imports: [
    RouterModule.forChild(adminState),
    PrepsAdminModule,
    PrepsSharedModule,
    CommonModule,
    GhaDashboardRoutingModule,
    PrepsPrepaymentEntryModule,
    DataExportModule,
    PrepsMaterialModule
  ],
  exports: [DashboardContainerComponent, UsersDashboardComponent],
  entryComponents: [
    DashboardContainerComponent,
    UsersDashboardComponent,
    MetricDashboardComponent,
    HealthDashboardComponent,
    PrepaymentsDashboardComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GhaDashboardModule {}
