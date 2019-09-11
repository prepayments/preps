import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardHudDeckRoutingModule } from './dashboard-hud-deck-routing.module';
import { DashboardHudComponent } from 'app/preps/gha-dashboard/dashboard-hud-container/dashboard-hud/dashboard-hud.component';
import { PrepsSharedModule } from 'app/shared';
import { DashboardHudOutstandingPrepsComponent } from './dashboard-hud-outstanding-preps/dashboard-hud-outstanding-preps.component';

@NgModule({
  declarations: [DashboardHudComponent, DashboardHudOutstandingPrepsComponent],
  exports: [DashboardHudComponent],
  imports: [CommonModule, PrepsSharedModule, DashboardHudDeckRoutingModule],
  entryComponents: [DashboardHudComponent, DashboardHudOutstandingPrepsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardHudContainerModule {}
