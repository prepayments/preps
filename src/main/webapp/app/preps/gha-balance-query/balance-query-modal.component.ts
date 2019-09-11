import { Component, OnDestroy, OnInit } from '@angular/core';
import { QuestionBase } from 'app/preps/model/question-base.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { BalanceQueryModelQuestionService } from 'app/preps/gha-balance-query/balance-query-model-question.service';
import { NGXLogger } from 'ngx-logger';
import { BalanceQuery, IBalanceQuery } from 'app/preps/model/balance-query.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RouteStateService } from 'app/preps/route-state.service';

/**
 * This is the component that displays the modal on which a dynamic form is drawn to exact
 * further details on the query that goes server-side
 */
@Component({
  selector: 'gha-balance-query-modal',
  templateUrl: './balance-query-modal.component.html',
  styleUrls: ['./balance-query-modal.component.scss']
})
export class BalanceQueryModalComponent implements OnInit, OnDestroy {
  questions: QuestionBase<any>[];
  balanceQuery: IBalanceQuery;
  isSaving: boolean;

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

  constructor(
    private fb: FormBuilder,
    private log: NGXLogger,
    private router: Router,
    private stateService: RouteStateService<IBalanceQuery>,
    // close modal if data entered has error
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.balanceQuery = {};
    this.questions = BalanceQueryModelQuestionService.getQuestions();
    this.log.debug(`Entering query for date of balance ...`);
  }

  ngOnDestroy(): void {
    this.questions = [];
    this.balanceQuery = undefined;
  }

  updateBalanceQuery(queryForm: FormGroup) {
    this.balanceQuery = BalanceQueryModalComponent.createFromForm(queryForm);
    this.log.debug(`Balance query updated for date : ${this.balanceQuery.balanceDate}`);

    this.enquire(this.balanceQuery);
  }

  enquire(balanceQuery: IBalanceQuery) {
    this.log.debug(
      `Navigating to balances for date : ${balanceQuery.balanceDate}; service outlet: ${balanceQuery.serviceOutlet}; account name: ${
        balanceQuery.accountName
      }`
    );

    this.stateService.data = balanceQuery;
    // TODO handle input errors
    const navigationExtras: NavigationExtras = {
      queryParams: {
        balanceDate: balanceQuery.balanceDate,
        serviceOutlet: balanceQuery.serviceOutlet,
        accountName: balanceQuery.accountName
      }
    };

    const navigation = this.router.navigate(['data-tables/prepayment-balances'], navigationExtras);

    this.reviewNavigation(navigation, balanceQuery);
  }

  private reviewNavigation(navigation: any, balanceQuery: IBalanceQuery): void {
    // note success
    navigation.then(() => {
      this.log.debug(`Navigation to '${balanceQuery} completed successfully`);
      this.modalService.dismissAll('Data table navigation complete!!!');
    });

    // catch unfortunate navigation incidences
    navigation.catch(() => {
      this.log.debug(`Navigation to '${balanceQuery} failed navigating back to previous view...`);
      this.previousState();
    });

    // clean up mmodals
    navigation.finally(() => {
      this.log.debug(`Navigation to '${balanceQuery} completed successfully`);
      this.modalService.dismissAll('In case dismissal has failed');
    });
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
