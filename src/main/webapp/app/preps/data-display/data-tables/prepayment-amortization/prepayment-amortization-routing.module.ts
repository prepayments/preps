import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AmortizationScheduleComponent } from 'app/preps/data-display/data-tables/prepayment-amortization/amortization-schedule.component';

const routes: Routes = [
  {
    path: 'amortization-schedule',
    component: AmortizationScheduleComponent,
    data: {
      pageTitle: 'Amortization Schedule Data'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PrepaymentAmortizationRoutingModule {}
