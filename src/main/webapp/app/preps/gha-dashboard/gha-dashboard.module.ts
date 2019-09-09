import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaDashboardRoutingModule } from './gha-dashboard-routing.module';
import { PrepsSharedModule } from 'app/shared';
import { DashboardContainerComponent } from './dashboard/dashboard-container.component';
import { UsersDashboardComponent } from './users-dashboard/users-dashboard.component';

@NgModule({
  declarations: [DashboardContainerComponent, UsersDashboardComponent],
  imports: [PrepsSharedModule, CommonModule, GhaDashboardRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [DashboardContainerComponent, UsersDashboardComponent],
  entryComponents: [DashboardContainerComponent, UsersDashboardComponent]
})
export class GhaDashboardModule {}
