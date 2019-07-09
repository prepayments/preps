import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaBalanceQueryRoutingModule } from './gha-balance-query-routing.module';
import { BalanceQueryModalComponent } from './balance-query-modal.component';

@NgModule({
  declarations: [BalanceQueryModalComponent],
  imports: [CommonModule, GhaBalanceQueryRoutingModule],
  exports: [BalanceQueryModalComponent],
  entryComponents: [BalanceQueryModalComponent]
})
export class GhaBalanceQueryModule {}
