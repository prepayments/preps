import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BalanceQueryModalComponent } from 'app/preps/gha-balance-query/balance-query-modal.component';

const routes: Routes = [
  {
    // TODO test purposes only
    // path: 'report/balances/prepayments/modal',
    // component: BalanceQueryModalComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GhaBalanceQueryRoutingModule {}
