import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';
import { AmortizationDataEntryFileService } from './amortization-data-entry-file.service';
import { AmortizationDataEntryFileComponent } from './amortization-data-entry-file.component';
import { AmortizationDataEntryFileDetailComponent } from './amortization-data-entry-file-detail.component';
import { AmortizationDataEntryFileUpdateComponent } from './amortization-data-entry-file-update.component';
import { AmortizationDataEntryFileDeletePopupComponent } from './amortization-data-entry-file-delete-dialog.component';
import { IAmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';

@Injectable({ providedIn: 'root' })
export class AmortizationDataEntryFileResolve implements Resolve<IAmortizationDataEntryFile> {
  constructor(private service: AmortizationDataEntryFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmortizationDataEntryFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmortizationDataEntryFile>) => response.ok),
        map((amortizationDataEntryFile: HttpResponse<AmortizationDataEntryFile>) => amortizationDataEntryFile.body)
      );
    }
    return of(new AmortizationDataEntryFile());
  }
}

export const amortizationDataEntryFileRoute: Routes = [
  {
    path: '',
    component: AmortizationDataEntryFileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AmortizationDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmortizationDataEntryFileDetailComponent,
    resolve: {
      amortizationDataEntryFile: AmortizationDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmortizationDataEntryFileUpdateComponent,
    resolve: {
      amortizationDataEntryFile: AmortizationDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmortizationDataEntryFileUpdateComponent,
    resolve: {
      amortizationDataEntryFile: AmortizationDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amortizationDataEntryFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmortizationDataEntryFileDeletePopupComponent,
    resolve: {
      amortizationDataEntryFile: AmortizationDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationDataEntryFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
