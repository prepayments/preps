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

@NgModule({
  declarations: [DashboardContainerComponent, UsersDashboardComponent, MetricDashboardComponent],
  imports: [
    RouterModule.forChild(adminState),
    PrepsAdminModule,
    PrepsSharedModule,
    CommonModule,
    GhaDashboardRoutingModule,
    PrepsMaterialModule
  ],
  exports: [DashboardContainerComponent, UsersDashboardComponent],
  entryComponents: [DashboardContainerComponent, UsersDashboardComponent, MetricDashboardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GhaDashboardModule {}
