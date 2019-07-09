import { Component, OnInit } from '@angular/core';
import { QuestionBase } from 'app/preps/model/question-base.model';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BalanceQueryModelQuestionService } from 'app/preps/gha-balance-query/balance-query-model-question.service';
import { NGXLogger } from 'ngx-logger';
import { IBalanceQuery } from 'app/preps/model/balance-query.model';

@Component({
  selector: 'gha-balance-query-modal',
  templateUrl: './balance-query-modal.component.html',
  styleUrls: ['./balance-query-modal.component.scss']
})
export class BalanceQueryModalComponent implements OnInit {
  questions: QuestionBase<any>[];
  balanceQuery: IBalanceQuery;
  isSaving: boolean;

  editForm = this.fb.group({
    balanceDate: [null, [Validators.required]],
    accountName: [],
    serviceOutlet: []
  });

  constructor(
    private fb: FormBuilder,
    private log: NGXLogger,
    protected activatedRoute: ActivatedRoute,
    protected questionService: BalanceQueryModelQuestionService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.balanceQuery = {};
    this.questions = this.questionService.getQuestions();

    this.log.debug(`Entering query for date of balance ...`);
  }

  enquire() {
    this.isSaving = true;
    const balanceDate = this.createFromForm();
    this.log.debug(`Query raised for the date : ${balanceDate}`);

    // TODO navigate to balance date
  }

  previousState() {
    window.history.back();
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  private createFromForm(): IBalanceQuery {
    return this.editForm.get(['balanceDate']).value;
  }
}
