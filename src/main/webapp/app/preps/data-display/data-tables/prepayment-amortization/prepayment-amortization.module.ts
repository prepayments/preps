import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrepaymentAmortizationRoutingModule } from './prepayment-amortization-routing.module';
import { AmortizationScheduleComponent } from './amortization-schedule.component';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { AmortizationScheduleQueryComponent } from './amortization-schedule-query/amortization-schedule-query.component';
import { PrepsSharedModule } from 'app/shared';

@NgModule({
  declarations: [AmortizationScheduleComponent, AmortizationScheduleQueryComponent],
  imports: [PrepsSharedModule, CommonModule, PrepaymentAmortizationRoutingModule, DTModule],
  exports: [AmortizationScheduleComponent, AmortizationScheduleQueryComponent],
  entryComponents: [AmortizationScheduleComponent, AmortizationScheduleQueryComponent]
})
export class PrepaymentAmortizationModule {}
