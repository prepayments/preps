import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardHudDeckRoutingModule } from './dashboard-hud-deck-routing.module';
import { DashboardHudComponent } from 'app/preps/gha-dashboard/dashboard-hud-container/dashboard-hud/dashboard-hud.component';
import { PrepsSharedModule } from 'app/shared';

@NgModule({
  declarations: [DashboardHudComponent],
  exports: [DashboardHudComponent],
  imports: [CommonModule, PrepsSharedModule, DashboardHudDeckRoutingModule],
  entryComponents: [DashboardHudComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardHudContainerModule {}
