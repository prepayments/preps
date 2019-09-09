import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaDashboardRoutingModule } from './gha-dashboard-routing.module';
import { PrepsSharedModule } from 'app/shared';
import { DashboardContainerComponent } from './dashboard/dashboard-container.component';
import { UsersDashboardComponent } from './users-dashboard/users-dashboard.component';
import { PrepsAdminModule } from 'app/admin/admin.module';
import { adminState } from 'app/admin';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [DashboardContainerComponent, UsersDashboardComponent],
  imports: [RouterModule.forChild(adminState), PrepsAdminModule, PrepsSharedModule, CommonModule, GhaDashboardRoutingModule],
  exports: [DashboardContainerComponent, UsersDashboardComponent],
  entryComponents: [DashboardContainerComponent, UsersDashboardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GhaDashboardModule {}
