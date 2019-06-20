import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { QuestionBase } from 'app/preps/model/question-base.model';
import { QuestionControlService } from 'app/preps/questionnaire/question-control.service';
import { FormFieldControlService } from 'app/preps/questionnaire/dynamic-form/form-field-control.service';
import { IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

@Component({
  selector: 'gha-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styles: []
})
export class DynamicFormComponent implements OnInit {
  @Input() questions: QuestionBase<any>[];
  @Output() model: EventEmitter<IAmortizationEntry> = new EventEmitter();
  fields: FormlyFieldConfig[] = [{}];
  queryForm: FormGroup;
  isSubmitting: boolean;

  constructor(private qcs: QuestionControlService, private ffcs: FormFieldControlService) {}

  ngOnInit() {
    this.fields = this.ffcs.toFormFieldConfig(this.questions);
    this.queryForm = this.qcs.toFormGroup(this.questions);
  }

  previousState() {
    window.history.back();
  }

  submit(model: IAmortizationEntry) {
    console.log(model);
    this.isSubmitting = true;

    this.model.emit(model);
  }

  protected onSubmitSuccess() {
    this.isSubmitting = false;
    this.previousState();
  }

  protected onSubmitError() {
    this.isSubmitting = false;
  }
}
