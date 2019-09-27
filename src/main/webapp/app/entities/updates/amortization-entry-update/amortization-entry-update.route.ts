import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';
import { AmortizationEntryUpdateService } from './amortization-entry-update.service';
import { AmortizationEntryUpdateComponent } from './amortization-entry-update.component';
import { AmortizationEntryUpdateDetailComponent } from './amortization-entry-update-detail.component';
import { AmortizationEntryUpdateUpdateComponent } from './amortization-entry-update-update.component';
import { AmortizationEntryUpdateDeletePopupComponent } from './amortization-entry-update-delete-dialog.component';
import { IAmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';

@Injectable({ providedIn: 'root' })
export class AmortizationEntryUpdateResolve implements Resolve<IAmortizationEntryUpdate> {
  constructor(private service: AmortizationEntryUpdateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmortizationEntryUpdate> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmortizationEntryUpdate>) => response.ok),
        map((amortizationEntryUpdate: HttpResponse<AmortizationEntryUpdate>) => amortizationEntryUpdate.body)
      );
    }
    return of(new AmortizationEntryUpdate());
  }
}

export const amortizationEntryUpdateRoute: Routes = [
  {
    path: '',
    component: AmortizationEntryUpdateComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AmortizationEntryUpdates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmortizationEntryUpdateDetailComponent,
    resolve: {
      amortizationEntryUpdate: AmortizationEntryUpdateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntryUpdates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmortizationEntryUpdateUpdateComponent,
    resolve: {
      amortizationEntryUpdate: AmortizationEntryUpdateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntryUpdates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmortizationEntryUpdateUpdateComponent,
    resolve: {
      amortizationEntryUpdate: AmortizationEntryUpdateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntryUpdates'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amortizationEntryUpdatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmortizationEntryUpdateDeletePopupComponent,
    resolve: {
      amortizationEntryUpdate: AmortizationEntryUpdateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntryUpdates'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
