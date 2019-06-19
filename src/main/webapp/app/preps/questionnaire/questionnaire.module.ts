import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionnaireRoutingModule } from './questionnaire-routing.module';
import { PrepsMaterialModule } from 'app/preps/preps-material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyMaterialModule } from '@ngx-formly/material';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    QuestionnaireRoutingModule,
    PrepsMaterialModule,
    ReactiveFormsModule,
    FormlyModule.forRoot({
      validationMessages: [{ name: 'required', message: 'This field is required' }]
    }),
    FormlyMaterialModule
  ],
  exports: [QuestionnaireRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QuestionnaireModule {}
