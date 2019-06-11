import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';
import { AmortizationEntryService } from './amortization-entry.service';
import { AmortizationEntryComponent } from './amortization-entry.component';
import { AmortizationEntryDetailComponent } from './amortization-entry-detail.component';
import { AmortizationEntryUpdateComponent } from './amortization-entry-update.component';
import { AmortizationEntryDeletePopupComponent } from './amortization-entry-delete-dialog.component';
import { IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

@Injectable({ providedIn: 'root' })
export class AmortizationEntryResolve implements Resolve<IAmortizationEntry> {
  constructor(private service: AmortizationEntryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmortizationEntry> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmortizationEntry>) => response.ok),
        map((amortizationEntry: HttpResponse<AmortizationEntry>) => amortizationEntry.body)
      );
    }
    return of(new AmortizationEntry());
  }
}

export const amortizationEntryRoute: Routes = [
  {
    path: '',
    component: AmortizationEntryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmortizationEntryDetailComponent,
    resolve: {
      amortizationEntry: AmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmortizationEntryUpdateComponent,
    resolve: {
      amortizationEntry: AmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmortizationEntryUpdateComponent,
    resolve: {
      amortizationEntry: AmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntries'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amortizationEntryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmortizationEntryDeletePopupComponent,
    resolve: {
      amortizationEntry: AmortizationEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationEntries'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
