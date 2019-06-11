import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';
import { PrepaymentEntryService } from './prepayment-entry.service';
import { PrepaymentEntryComponent } from './prepayment-entry.component';
import { PrepaymentEntryDetailComponent } from './prepayment-entry-detail.component';
import { PrepaymentEntryUpdateComponent } from './prepayment-entry-update.component';
import { PrepaymentEntryDeletePopupComponent } from './prepayment-entry-delete-dialog.component';
import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

@Injectable({ providedIn: 'root' })
export class PrepaymentEntryResolve implements Resolve<IPrepaymentEntry> {
  constructor(private service: PrepaymentEntryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPrepaymentEntry> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PrepaymentEntry>) => response.ok),
        map((prepaymentEntry: HttpResponse<PrepaymentEntry>) => prepaymentEntry.body)
      );
    }
    return of(new PrepaymentEntry());
  }
}

export const prepaymentEntryRoute: Routes = [
  {
    path: '',
    component: PrepaymentEntryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrepaymentEntryDetailComponent,
    resolve: {
      prepaymentEntry: PrepaymentEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrepaymentEntryUpdateComponent,
    resolve: {
      prepaymentEntry: PrepaymentEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrepaymentEntryUpdateComponent,
    resolve: {
      prepaymentEntry: PrepaymentEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentEntries'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const prepaymentEntryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PrepaymentEntryDeletePopupComponent,
    resolve: {
      prepaymentEntry: PrepaymentEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrepaymentEntries'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
