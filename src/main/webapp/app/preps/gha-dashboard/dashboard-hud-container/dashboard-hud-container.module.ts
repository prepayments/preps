import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardHudDeckRoutingModule } from './dashboard-hud-deck-routing.module';
import { DashboardHudComponent } from 'app/preps/gha-dashboard/dashboard-hud-container/dashboard-hud/dashboard-hud.component';
import { PrepsSharedModule } from 'app/shared';
import { DashboardHudOutstandingPrepsComponent } from './dashboard-hud-outstanding-preps/dashboard-hud-outstanding-preps.component';
import { DashboardHudAmortizationComponent } from './dashboard-hud-amortization/dashboard-hud-amortization.component';

@NgModule({
  declarations: [DashboardHudComponent, DashboardHudOutstandingPrepsComponent, DashboardHudAmortizationComponent],
  exports: [DashboardHudComponent],
  imports: [CommonModule, PrepsSharedModule, DashboardHudDeckRoutingModule],
  entryComponents: [DashboardHudComponent, DashboardHudOutstandingPrepsComponent, DashboardHudAmortizationComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardHudContainerModule {}
