import { Component, OnInit } from '@angular/core';
import { DropdownQuestion, QuestionBase, TextBoxQuestion } from 'app/preps/model/question-base.model';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BalanceQueryModelQuestionService } from 'app/preps/gha-balance-query/balance-query-model-question.service';

@Component({
  selector: 'gha-balance-query-modal',
  templateUrl: './balance-query-modal.component.html',
  styleUrls: ['./balance-query-modal.component.scss']
})
export class BalanceQueryModalComponent implements OnInit {
  questions: QuestionBase<any>[];

  balanceDate: string;

  isSaving: boolean;

  editForm = this.fb.group({
    balanceDate: [null, [Validators.required]]
  });

  constructor(
    private fb: FormBuilder,
    protected activatedRoute: ActivatedRoute,
    protected questionService: BalanceQueryModelQuestionService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.balanceDate = '';
    this.questions = this.questionService.getQuestions();
  }
}
