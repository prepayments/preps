import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrepaymentAmortizationRoutingModule } from './prepayment-amortization-routing.module';
import { AmortizationScheduleComponent } from './amortization-schedule.component';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { AmortizationScheduleQueryComponent } from './amortization-schedule-query/amortization-schedule-query.component';
import { PrepsSharedModule } from 'app/shared';
import { PrepsMaterialModule } from 'app/preps/preps-material.module';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { QuestionnaireModule } from 'app/preps/questionnaire/questionnaire.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AmortizationScheduleComponent, AmortizationScheduleQueryComponent],
  imports: [
    QuestionnaireModule,
    PrepsMaterialModule,
    ReactiveFormsModule,
    FormlyModule.forRoot({
      validationMessages: [{ name: 'required', message: 'This field is required' }]
    }),
    FormlyMaterialModule,
    PrepsSharedModule,
    CommonModule,
    PrepaymentAmortizationRoutingModule,
    DTModule
  ],
  exports: [AmortizationScheduleComponent, AmortizationScheduleQueryComponent],
  entryComponents: [AmortizationScheduleComponent, AmortizationScheduleQueryComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepaymentAmortizationModule {}
