import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrepsRoutingModule } from './preps-routing.module';
import { DataExportModule } from './data-export/data-export.module';
import { ReportingModule } from './reporting/reporting.module';
import { NavigationModule } from './navigation/navigation.module';

@NgModule({
    declarations: [],
    imports: [CommonModule, PrepsRoutingModule, DataExportModule, ReportingModule, NavigationModule],
    exports: [PrepsRoutingModule, DataExportModule, ReportingModule, NavigationModule]
})
export class PrepsModule {}
