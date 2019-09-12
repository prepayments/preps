import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrepaymentAmortizationRoutingModule } from './prepayment-amortization-routing.module';
import { AmortizationScheduleComponent } from './amortization-schedule.component';
import { DataTablesModule as DTModule } from 'angular-datatables';

@NgModule({
  declarations: [AmortizationScheduleComponent],
  imports: [CommonModule, PrepaymentAmortizationRoutingModule, DTModule],
  exports: [AmortizationScheduleComponent],
  entryComponents: [AmortizationScheduleComponent]
})
export class PrepaymentAmortizationModule {}
