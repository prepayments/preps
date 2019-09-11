import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataDisplayRoutingModule } from './data-display-routing.module';
import { DataTablesModule } from './data-tables/data-tables.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, DataDisplayRoutingModule, DataTablesModule]
})
export class DataDisplayModule {}
