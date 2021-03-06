import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataDisplayRoutingModule } from './data-display-routing.module';
import { DataTablesModule } from './data-tables/data-tables.module';
import { DataExportModule } from 'app/preps/data-display/data-export/data-export.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PrepsMaterialModule } from 'app/preps/preps-material.module';
import { PrepsSharedModule } from 'app/shared';
import { PrepaymentAmortizationModule } from './data-tables/prepayment-amortization/prepayment-amortization.module';

@NgModule({
  declarations: [],
  imports: [
    PrepsMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    PrepsSharedModule,
    DataExportModule,
    DataDisplayRoutingModule,
    DataTablesModule,
    PrepaymentAmortizationModule
  ],
  exports: [DataExportModule, DataTablesModule]
})
export class DataDisplayModule {}
