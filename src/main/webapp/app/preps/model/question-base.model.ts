/**
 * Represents metadata for the forms
 */
export class QuestionBase<T> {
  value: T;
  key: string;
  label: string;
  required: boolean;
  order: number;
  fieldType: string;
  controlType: string;

  constructor(
    options: {
      value?: T;
      key?: string;
      label?: string;
      required?: boolean;
      order?: number;
      fieldType?: string;
      controlType?: string;
    } = {}
  ) {
    this.value = options.value;
    this.key = options.key || '';
    this.label = options.label || '';
    this.required = !!options.required;
    this.order = options.order === undefined ? 1 : options.order;
    this.fieldType = options.fieldType;
    this.controlType = options.controlType || '';
  }
}

export class TextBoxQuestion extends QuestionBase<string> {
  controlType = 'textbox';
  type: string;

  constructor(options: {} = {}) {
    super(options);
    /* tslint:disable:no-string-literal */
    this.type = options['type'] || '';
    /* tslint:enable:no-string-literal */
  }
}

export class DropdownQuestion extends QuestionBase<string> {
  controlType = 'dropdown';
  options: { key: string; value: string }[] = [];

  constructor(options: {} = {}) {
    super(options);
    /* tslint:disable:no-string-literal */
    this.options = options['options'] || [];
    /* tslint:enable:no-string-literal */
  }
}
