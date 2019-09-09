import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaDashboardRoutingModule } from './gha-dashboard-routing.module';
import { PrepsSharedModule } from 'app/shared';
import { DashboardComponent } from './dashboard/dashboard.component';

@NgModule({
  declarations: [DashboardComponent],
  imports: [PrepsSharedModule, CommonModule, GhaDashboardRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [DashboardComponent],
  entryComponents: [DashboardComponent]
})
export class GhaDashboardModule {}
