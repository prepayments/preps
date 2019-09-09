import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaDashboardRoutingModule } from './gha-dashboard-routing.module';
import { PrepsSharedModule } from 'app/shared';

@NgModule({
  declarations: [],
  imports: [PrepsSharedModule, CommonModule, GhaDashboardRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GhaDashboardModule {}
