import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataTablesRoutingModule } from './data-tables-routing.module';
import { PrepaymentBalanceComponent } from 'app/preps/data-display/data-tables/prepayment-balance/prepayment-balance.component';
import { PrepsSharedModule } from 'app/shared';
import { DataTablesModule as DTModule } from 'angular-datatables';

@NgModule({
  declarations: [PrepaymentBalanceComponent],
  imports: [CommonModule, PrepsSharedModule, DTModule, DataTablesRoutingModule],
  exports: [DataTablesRoutingModule, PrepaymentBalanceComponent],
  entryComponents: [PrepaymentBalanceComponent]
})
export class DataTablesModule {}
