import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';
import { TransactionAccountService } from './transaction-account.service';
import { TransactionAccountComponent } from './transaction-account.component';
import { TransactionAccountDetailComponent } from './transaction-account-detail.component';
import { TransactionAccountUpdateComponent } from './transaction-account-update.component';
import { TransactionAccountDeletePopupComponent } from './transaction-account-delete-dialog.component';
import { ITransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';

@Injectable({ providedIn: 'root' })
export class TransactionAccountResolve implements Resolve<ITransactionAccount> {
  constructor(private service: TransactionAccountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionAccount> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionAccount>) => response.ok),
        map((transactionAccount: HttpResponse<TransactionAccount>) => transactionAccount.body)
      );
    }
    return of(new TransactionAccount());
  }
}

export const transactionAccountRoute: Routes = [
  {
    path: '',
    component: TransactionAccountComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionAccountDetailComponent,
    resolve: {
      transactionAccount: TransactionAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionAccountUpdateComponent,
    resolve: {
      transactionAccount: TransactionAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionAccountUpdateComponent,
    resolve: {
      transactionAccount: TransactionAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccounts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionAccountPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionAccountDeletePopupComponent,
    resolve: {
      transactionAccount: TransactionAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
