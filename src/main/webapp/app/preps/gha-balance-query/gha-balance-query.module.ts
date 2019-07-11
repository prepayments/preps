import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaBalanceQueryRoutingModule } from './gha-balance-query-routing.module';
import { BalanceQueryModalComponent } from './balance-query-modal.component';
import { QuestionnaireModule } from 'app/preps/questionnaire/questionnaire.module';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { ReactiveFormsModule } from '@angular/forms';
import { PrepsMaterialModule } from 'app/preps/preps-material.module';
import { FormlyModule } from '@ngx-formly/core';

/**
 * This module is about intercepting calls to retrieve balance of prepayments
 * and is used to intercept such a call in order to enrich the query with
 * parameters entered into a form as criteria for balance query answering the
 * following questions:
 * You want the prepayment balance as at when?
 * You want the prepayment balance for that date for which service outlet?
 * You want the prepayment for prepayment balances for which account?
 *
 */
@NgModule({
  declarations: [BalanceQueryModalComponent],
  imports: [
    CommonModule,
    GhaBalanceQueryRoutingModule,
    QuestionnaireModule,
    PrepsMaterialModule,
    ReactiveFormsModule,
    FormlyModule.forRoot({
      validationMessages: [{ name: 'required', message: 'This field is required' }]
    }),
    FormlyMaterialModule
  ],
  exports: [BalanceQueryModalComponent],
  entryComponents: [BalanceQueryModalComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GhaBalanceQueryModule {}
