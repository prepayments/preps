import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaDashboardRoutingModule } from './gha-dashboard-routing.module';
import { PrepsSharedModule } from 'app/shared';
import { DashboardContainerComponent } from './dashboard/dashboard-container.component';

@NgModule({
  declarations: [DashboardContainerComponent],
  imports: [PrepsSharedModule, CommonModule, GhaDashboardRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [DashboardContainerComponent],
  entryComponents: [DashboardContainerComponent]
})
export class GhaDashboardModule {}
