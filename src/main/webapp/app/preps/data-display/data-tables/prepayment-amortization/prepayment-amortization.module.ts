import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrepaymentAmortizationRoutingModule } from './prepayment-amortization-routing.module';
import { AmortizationScheduleComponent } from './amortization-schedule.component';

@NgModule({
  declarations: [AmortizationScheduleComponent],
  imports: [CommonModule, PrepaymentAmortizationRoutingModule],
  exports: [AmortizationScheduleComponent],
  entryComponents: [AmortizationScheduleComponent]
})
export class PrepaymentAmortizationModule {}
