import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { QuestionBase } from 'app/preps/model/question-base.model';
import { QuestionControlService } from 'app/preps/questionnaire/question-control.service';

@Component({
  selector: 'gha-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styles: []
})
export class DynamicFormComponent implements OnInit {
  @Input() questions: QuestionBase<any>[];
  @Input() model;
  fields: FormlyFieldConfig[] = [{}];
  queryForm: FormGroup;
  isSubmitting: boolean;

  constructor(private qcs: QuestionControlService) {}

  ngOnInit() {
    this.questions.forEach((question: QuestionBase<any>) => {
      this.fields.push({
        key: question.key,
        type: question.fieldType,
        templateOptions: {
          required: question.required,
          label: question.label,
          options: question.selectOptions
        }
      });
    });

    this.queryForm = this.qcs.toFormGroup(this.questions);
  }

  previousState() {
    window.history.back();
  }

  submit() {
    console.log(this.model);
    this.isSubmitting = true;
    // TODO Other awesome stuff
  }

  protected onSubmitSuccess() {
    this.isSubmitting = false;
    this.previousState();
  }

  protected onSubmitError() {
    this.isSubmitting = false;
  }
}
