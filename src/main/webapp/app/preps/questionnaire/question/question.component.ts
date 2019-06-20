import { Component, Input, OnInit } from '@angular/core';
import { QuestionBase } from 'app/preps/model/question-base.model';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';

@Component({
  selector: 'gha-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  @Input() question: QuestionBase<any>;
  @Input() form: FormGroup;

  model = {};

  fields: FormlyFieldConfig[];

  constructor() {}

  ngOnInit() {
    this.fields = [
      {
        // key: 'name',
        key: this.question.key,
        // type: 'input',
        type: this.question.controlType,
        templateOptions: {
          // required: true,
          required: this.question.required,
          // label: 'First Name'
          label: this.question.label
        }
      }
    ];
  }

  get isValid() {
    return this.form.controls[this.question.key].valid;
  }
}
