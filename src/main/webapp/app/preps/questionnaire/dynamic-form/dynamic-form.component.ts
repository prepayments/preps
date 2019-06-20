import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { QuestionBase, TextBoxQuestion } from 'app/preps/model/question-base.model';
import { QuestionControlService } from 'app/preps/questionnaire/question-control.service';

@Component({
  selector: 'gha-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styles: []
})
export class DynamicFormComponent implements OnInit {
  @Input() question: QuestionBase<any>;
  @Input() model;
  queryForm: FormGroup;
  isSubmitting: boolean;

  fields: FormlyFieldConfig[];

  constructor(private qcs: QuestionControlService) {}

  ngOnInit() {
    this.fields = [
      {
        key: this.question.key,
        type: this.question.fieldType,
        templateOptions: {
          required: this.question.required,
          label: this.question.label
        }
      }
    ];

    this.queryForm = this.qcs.toFormGroup([this.question]);
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
