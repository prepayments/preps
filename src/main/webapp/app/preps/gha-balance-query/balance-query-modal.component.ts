import { Component, OnInit } from '@angular/core';
import { QuestionBase } from 'app/preps/model/question-base.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { BalanceQueryModelQuestionService } from 'app/preps/gha-balance-query/balance-query-model-question.service';
import { NGXLogger } from 'ngx-logger';
import { BalanceQuery, IBalanceQuery } from 'app/preps/model/balance-query.model';
import { SERVER_API_URL } from 'app/app.constants';

@Component({
  selector: 'gha-balance-query-modal',
  templateUrl: './balance-query-modal.component.html',
  styleUrls: ['./balance-query-modal.component.scss']
})
export class BalanceQueryModalComponent implements OnInit {
  questions: QuestionBase<any>[];
  balanceQuery: IBalanceQuery;
  isSaving: boolean;

  // prepaymentBalanceUrl = SERVER_API_URL + '/data-export/prepayment-balances';

  editForm = this.fb.group({
    balanceDate: [null, [Validators.required]],
    accountName: [],
    serviceOutlet: []
  });

  private static createFromForm(queryForm: FormGroup): IBalanceQuery {
    return new BalanceQuery({
      balanceDate: queryForm.get(['balanceDate']).value,
      accountName: queryForm.get(['accountName']).value,
      serviceOutlet: queryForm.get(['serviceOutlet']).value
    });
  }

  constructor(private fb: FormBuilder, private log: NGXLogger, private router: Router) {}

  ngOnInit() {
    this.isSaving = false;
    this.balanceQuery = {};
    this.questions = BalanceQueryModelQuestionService.getQuestions();
    this.log.debug(`Entering query for date of balance ...`);
  }

  updateBalanceQuery(queryForm: FormGroup) {
    this.balanceQuery = BalanceQueryModalComponent.createFromForm(queryForm);
    this.log.debug(`Balance query updated for date : ${this.balanceQuery.balanceDate}`);

    this.enquire();
  }

  enquire() {
    this.log.debug(
      `Navigating to balances for date : ${this.balanceQuery.balanceDate}; service outlet: ${
        this.balanceQuery.serviceOutlet
      }; account name: ${this.balanceQuery.accountName}`
    );

    const navigationExtras: NavigationExtras = {
      queryParams: this.balanceQuery
    };

    this.router.navigate(['data-export/prepayment-balances'], navigationExtras);
  }

  previousState() {
    window.history.back();
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
