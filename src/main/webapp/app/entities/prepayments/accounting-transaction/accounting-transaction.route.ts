import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';
import { AccountingTransactionService } from './accounting-transaction.service';
import { AccountingTransactionComponent } from './accounting-transaction.component';
import { AccountingTransactionDetailComponent } from './accounting-transaction-detail.component';
import { AccountingTransactionUpdateComponent } from './accounting-transaction-update.component';
import { AccountingTransactionDeletePopupComponent } from './accounting-transaction-delete-dialog.component';
import { IAccountingTransaction } from 'app/shared/model/prepayments/accounting-transaction.model';

@Injectable({ providedIn: 'root' })
export class AccountingTransactionResolve implements Resolve<IAccountingTransaction> {
  constructor(private service: AccountingTransactionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountingTransaction> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AccountingTransaction>) => response.ok),
        map((accountingTransaction: HttpResponse<AccountingTransaction>) => accountingTransaction.body)
      );
    }
    return of(new AccountingTransaction());
  }
}

export const accountingTransactionRoute: Routes = [
  {
    path: '',
    component: AccountingTransactionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountingTransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountingTransactionDetailComponent,
    resolve: {
      accountingTransaction: AccountingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountingTransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountingTransactionUpdateComponent,
    resolve: {
      accountingTransaction: AccountingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountingTransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountingTransactionUpdateComponent,
    resolve: {
      accountingTransaction: AccountingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountingTransactions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountingTransactionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountingTransactionDeletePopupComponent,
    resolve: {
      accountingTransaction: AccountingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountingTransactions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
