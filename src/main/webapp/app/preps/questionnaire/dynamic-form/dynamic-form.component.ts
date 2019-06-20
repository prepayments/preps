import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { QuestionBase } from 'app/preps/model/question-base.model';

@Component({
  selector: 'gha-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styles: []
})
export class DynamicFormComponent implements OnInit {
  @Input() question: QuestionBase<any>;
  form = new FormGroup({});

  isSubmitting: boolean;

  model = {};
  fields: FormlyFieldConfig[];

  constructor() {}

  ngOnInit() {
    this.fields = [
      {
        key: this.question.key,
        type: this.question.controlType,
        templateOptions: {
          required: this.question.required,
          label: this.question.label
        }
      }
    ];
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
