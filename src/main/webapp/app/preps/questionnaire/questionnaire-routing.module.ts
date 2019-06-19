import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DynamicFormComponent } from 'app/preps/questionnaire/dynamic-form/dynamic-form.component';

/*TODO Remove this path which is for test purposes only*/
const routes: Routes = [
  {
    path: 'questionnaire',
    component: DynamicFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuestionnaireRoutingModule {}
