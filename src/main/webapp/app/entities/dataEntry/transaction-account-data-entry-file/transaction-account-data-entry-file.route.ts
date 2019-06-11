import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionAccountDataEntryFile } from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';
import { TransactionAccountDataEntryFileService } from './transaction-account-data-entry-file.service';
import { TransactionAccountDataEntryFileComponent } from './transaction-account-data-entry-file.component';
import { TransactionAccountDataEntryFileDetailComponent } from './transaction-account-data-entry-file-detail.component';
import { TransactionAccountDataEntryFileUpdateComponent } from './transaction-account-data-entry-file-update.component';
import { TransactionAccountDataEntryFileDeletePopupComponent } from './transaction-account-data-entry-file-delete-dialog.component';
import { ITransactionAccountDataEntryFile } from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';

@Injectable({ providedIn: 'root' })
export class TransactionAccountDataEntryFileResolve implements Resolve<ITransactionAccountDataEntryFile> {
  constructor(private service: TransactionAccountDataEntryFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionAccountDataEntryFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionAccountDataEntryFile>) => response.ok),
        map((transactionAccountDataEntryFile: HttpResponse<TransactionAccountDataEntryFile>) => transactionAccountDataEntryFile.body)
      );
    }
    return of(new TransactionAccountDataEntryFile());
  }
}

export const transactionAccountDataEntryFileRoute: Routes = [
  {
    path: '',
    component: TransactionAccountDataEntryFileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'TransactionAccountDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionAccountDataEntryFileDetailComponent,
    resolve: {
      transactionAccountDataEntryFile: TransactionAccountDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccountDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionAccountDataEntryFileUpdateComponent,
    resolve: {
      transactionAccountDataEntryFile: TransactionAccountDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccountDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionAccountDataEntryFileUpdateComponent,
    resolve: {
      transactionAccountDataEntryFile: TransactionAccountDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccountDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionAccountDataEntryFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionAccountDataEntryFileDeletePopupComponent,
    resolve: {
      transactionAccountDataEntryFile: TransactionAccountDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionAccountDataEntryFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
