import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaBalanceQueryRoutingModule } from './gha-balance-query-routing.module';
import { BalanceQueryModalComponent } from './balance-query-modal.component';
import { QuestionnaireModule } from 'app/preps/questionnaire/questionnaire.module';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { ReactiveFormsModule } from '@angular/forms';
import { PrepsMaterialModule } from 'app/preps/preps-material.module';
import { FormlyModule } from '@ngx-formly/core';

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
