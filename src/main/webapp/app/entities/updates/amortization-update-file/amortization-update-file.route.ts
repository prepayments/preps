import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';
import { AmortizationUpdateFileService } from './amortization-update-file.service';
import { AmortizationUpdateFileComponent } from './amortization-update-file.component';
import { AmortizationUpdateFileDetailComponent } from './amortization-update-file-detail.component';
import { AmortizationUpdateFileUpdateComponent } from './amortization-update-file-update.component';
import { AmortizationUpdateFileDeletePopupComponent } from './amortization-update-file-delete-dialog.component';
import { IAmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';

@Injectable({ providedIn: 'root' })
export class AmortizationUpdateFileResolve implements Resolve<IAmortizationUpdateFile> {
  constructor(private service: AmortizationUpdateFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmortizationUpdateFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmortizationUpdateFile>) => response.ok),
        map((amortizationUpdateFile: HttpResponse<AmortizationUpdateFile>) => amortizationUpdateFile.body)
      );
    }
    return of(new AmortizationUpdateFile());
  }
}

export const amortizationUpdateFileRoute: Routes = [
  {
    path: '',
    component: AmortizationUpdateFileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AmortizationUpdateFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmortizationUpdateFileDetailComponent,
    resolve: {
      amortizationUpdateFile: AmortizationUpdateFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUpdateFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmortizationUpdateFileUpdateComponent,
    resolve: {
      amortizationUpdateFile: AmortizationUpdateFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUpdateFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmortizationUpdateFileUpdateComponent,
    resolve: {
      amortizationUpdateFile: AmortizationUpdateFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUpdateFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amortizationUpdateFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmortizationUpdateFileDeletePopupComponent,
    resolve: {
      amortizationUpdateFile: AmortizationUpdateFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUpdateFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
