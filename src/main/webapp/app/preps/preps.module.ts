import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrepsRoutingModule } from './preps-routing.module';
import { DataExportModule } from './data-export/data-export.module';
import { ReportingModule } from './reporting/reporting.module';
import { NavigationModule } from './navigation/navigation.module';
import { TypeAheadComponent } from './type-ahead/type-ahead.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PrepsMaterialModule } from 'app/preps/preps-material.module';
import { QuestionnaireModule } from 'app/preps/questionnaire/questionnaire.module';
import { GhaBalanceQueryModule } from './gha-balance-query/gha-balance-query.module';
import { AboutPrepsModule } from './about-preps/about-preps.module';
import { GhaDashboardModule } from './gha-dashboard/gha-dashboard.module';
import { DataDisplayModule } from './data-display/data-display.module';

@NgModule({
  declarations: [TypeAheadComponent],
  imports: [
    CommonModule,
    PrepsMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    PrepsRoutingModule,
    DataExportModule,
    ReportingModule,
    NavigationModule,
    QuestionnaireModule,
    GhaBalanceQueryModule,
    AboutPrepsModule,
    GhaDashboardModule,
    DataDisplayModule
  ],
  exports: [
    PrepsRoutingModule,
    DataExportModule,
    ReportingModule,
    NavigationModule,
    PrepsMaterialModule,
    QuestionnaireModule,
    AboutPrepsModule,
    GhaDashboardModule
  ]
})
export class PrepsModule {}
