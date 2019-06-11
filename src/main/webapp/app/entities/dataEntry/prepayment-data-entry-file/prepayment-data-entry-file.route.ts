import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';
import { PrepaymentDataEntryFileService } from './prepayment-data-entry-file.service';
import { PrepaymentDataEntryFileComponent } from './prepayment-data-entry-file.component';
import { PrepaymentDataEntryFileDetailComponent } from './prepayment-data-entry-file-detail.component';
import { PrepaymentDataEntryFileUpdateComponent } from './prepayment-data-entry-file-update.component';
import { PrepaymentDataEntryFileDeletePopupComponent } from './prepayment-data-entry-file-delete-dialog.component';
import { IPrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';

@Injectable({ providedIn: 'root' })
export class PrepaymentDataEntryFileResolve implements Resolve<IPrepaymentDataEntryFile> {
  constructor(private service: PrepaymentDataEntryFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPrepaymentDataEntryFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PrepaymentDataEntryFile>) => response.ok),
        map((prepaymentDataEntryFile: HttpResponse<PrepaymentDataEntryFile>) => prepaymentDataEntryFile.body)
      );
    }
    return of(new PrepaymentDataEntryFile());
  }
}

export const prepaymentDataEntryFileRoute: Routes = [
  {
    path: '',
    component: PrepaymentDataEntryFileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'PrepaymentDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrepaymentDataEntryFileDetailComponent,
    resolve: {
      prepaymentDataEntryFile: PrepaymentDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrepaymentDataEntryFileUpdateComponent,
    resolve: {
      prepaymentDataEntryFile: PrepaymentDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrepaymentDataEntryFileUpdateComponent,
    resolve: {
      prepaymentDataEntryFile: PrepaymentDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const prepaymentDataEntryFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PrepaymentDataEntryFileDeletePopupComponent,
    resolve: {
      prepaymentDataEntryFile: PrepaymentDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentDataEntryFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
