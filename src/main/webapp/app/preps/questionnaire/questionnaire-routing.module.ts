import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DynamicFormComponent } from 'app/preps/questionnaire/dynamic-form/dynamic-form.component';
import { ServiceOutletComponent } from 'app/entities/prepayments/service-outlet';
import { ServiceOutletUpdateComponent } from 'app/preps/questionnaire/test-client/service-outlet-update.component';

/*TODO Remove this path which is for test purposes only*/
const routes: Routes = [
  {
    path: 'questionnaire',
    component: DynamicFormComponent
  },
  {
    path: 'test-client',
    component: ServiceOutletUpdateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuestionnaireRoutingModule {}
