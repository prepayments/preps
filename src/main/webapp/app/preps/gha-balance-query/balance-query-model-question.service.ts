import { Injectable } from '@angular/core';
import { QuestionBase, TextBoxQuestion } from 'app/preps/model/question-base.model';

/**
 * This service is used to provide field values in order for the QuestionnaireModule
 * to construct a dynamic form in the modal deployed
 */
@Injectable({
  providedIn: 'root'
})
export class BalanceQueryModelQuestionService {
  /**
   * Returns the fields to be queried
   *
   * @returns {QuestionBase<any>[]}
   */
  static getQuestions(): QuestionBase<any>[] {
    const questions: QuestionBase<any>[] = [
      new TextBoxQuestion({
        key: 'balanceDate',
        label: 'Balance Date',
        type: 'date',
        fieldType: 'input',
        order: 1
      }),
      new TextBoxQuestion({
        key: 'accountName',
        label: 'Account Name',
        type: 'text',
        fieldType: 'input',
        order: 3
      }),
      new TextBoxQuestion({
        key: 'serviceOutlet',
        label: 'Service Outlet Code',
        type: 'text',
        fieldType: 'input',
        order: 2
      })
    ];
    return questions.sort((a, b) => a.order - b.order);
  }
}
