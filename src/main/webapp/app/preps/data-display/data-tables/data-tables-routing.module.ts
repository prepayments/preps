import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PrepaymentBalanceComponent } from 'app/preps/data-display/data-tables/prepayment-balance/prepayment-balance.component';

const routes: Routes = [
  {
    path: 'prepayment-balances',
    component: PrepaymentBalanceComponent,
    data: {
      pageTitle: 'Data | Prepayment Balances'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DataTablesRoutingModule {}
