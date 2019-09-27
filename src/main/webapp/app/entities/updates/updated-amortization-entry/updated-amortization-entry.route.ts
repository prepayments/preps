import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';
import { UpdatedAmortizationEntryService } from './updated-amortization-entry.service';
import { UpdatedAmortizationEntryComponent } from './updated-amortization-entry.component';
import { UpdatedAmortizationEntryDetailComponent } from './updated-amortization-entry-detail.component';
import { UpdatedAmortizationEntryUpdateComponent } from './updated-amortization-entry-update.component';
import { UpdatedAmortizationEntryDeletePopupComponent } from './updated-amortization-entry-delete-dialog.component';
import { IUpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';

@Injectable({ providedIn: 'root' })
export class UpdatedAmortizationEntryResolve implements Resolve<IUpdatedAmortizationEntry> {
  constructor(private service: UpdatedAmortizationEntryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUpdatedAmortizationEntry> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UpdatedAmortizationEntry>) => response.ok),
        map((updatedAmortizationEntry: HttpResponse<UpdatedAmortizationEntry>) => updatedAmortizationEntry.body)
      );
    }
    return of(new UpdatedAmortizationEntry());
  }
}

export const updatedAmortizationEntryRoute: Routes = [
  {
    path: '',
    component: UpdatedAmortizationEntryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'UpdatedAmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UpdatedAmortizationEntryDetailComponent,
    resolve: {
      updatedAmortizationEntry: UpdatedAmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UpdatedAmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UpdatedAmortizationEntryUpdateComponent,
    resolve: {
      updatedAmortizationEntry: UpdatedAmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'UpdatedAmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UpdatedAmortizationEntryUpdateComponent,
    resolve: {
      updatedAmortizationEntry: UpdatedAmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'UpdatedAmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const updatedAmortizationEntryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UpdatedAmortizationEntryDeletePopupComponent,
    resolve: {
      updatedAmortizationEntry: UpdatedAmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'UpdatedAmortizationEntries'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
