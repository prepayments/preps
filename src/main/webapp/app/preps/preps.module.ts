import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrepsRoutingModule } from './preps-routing.module';
import { DataExportModule } from './data-export/data-export.module';
import { ReportingModule } from './reporting/reporting.module';
import { NavigationModule } from './navigation/navigation.module';
import { TypeAheadComponent } from './type-ahead/type-ahead.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [TypeAheadComponent],
  imports: [CommonModule, ReactiveFormsModule, FormsModule, PrepsRoutingModule, DataExportModule, ReportingModule, NavigationModule],
  exports: [PrepsRoutingModule, DataExportModule, ReportingModule, NavigationModule]
})
export class PrepsModule {}
