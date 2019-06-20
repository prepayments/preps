import { Injectable } from '@angular/core';
import { DropdownQuestion, QuestionBase, TextBoxQuestion } from 'app/preps/model/question-base.model';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  public getQuestions(): QuestionBase<any>[] {
    const questions: QuestionBase<any>[] = [
      new DropdownQuestion({
        key: 'brave',
        label: 'Bravery Rating',
        required: false,
        fieldType: 'select',
        options: [
          { name: 'solid', value: 'Solid' },
          { name: 'great', value: 'Great' },
          { name: 'good', value: 'Good' },
          { name: 'unproven', value: 'Unproven' }
        ],
        order: 3
      }),
      new TextBoxQuestion({
        key: 'firstName',
        label: 'First Name',
        value: 'Bombasto',
        required: true,
        fieldType: 'input',
        order: 4
      }),
      new TextBoxQuestion({
        key: 'lastName',
        label: 'Last Name',
        value: 'Bombasto',
        required: true,
        fieldType: 'input',
        order: 2
      }),
      new TextBoxQuestion({
        key: 'emailAddress',
        label: 'email',
        type: 'email',
        fieldType: 'input',
        order: 1
      })
    ];
    return questions.sort((a, b) => a.order - b.order);
  }
}
