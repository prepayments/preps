import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';
import { AmortizationUploadService } from './amortization-upload.service';
import { AmortizationUploadComponent } from './amortization-upload.component';
import { AmortizationUploadDetailComponent } from './amortization-upload-detail.component';
import { AmortizationUploadUpdateComponent } from './amortization-upload-update.component';
import { AmortizationUploadDeletePopupComponent } from './amortization-upload-delete-dialog.component';
import { IAmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';

@Injectable({ providedIn: 'root' })
export class AmortizationUploadResolve implements Resolve<IAmortizationUpload> {
  constructor(private service: AmortizationUploadService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmortizationUpload> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmortizationUpload>) => response.ok),
        map((amortizationUpload: HttpResponse<AmortizationUpload>) => amortizationUpload.body)
      );
    }
    return of(new AmortizationUpload());
  }
}

export const amortizationUploadRoute: Routes = [
  {
    path: '',
    component: AmortizationUploadComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AmortizationUploads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmortizationUploadDetailComponent,
    resolve: {
      amortizationUpload: AmortizationUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmortizationUploadUpdateComponent,
    resolve: {
      amortizationUpload: AmortizationUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmortizationUploadUpdateComponent,
    resolve: {
      amortizationUpload: AmortizationUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploads'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amortizationUploadPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmortizationUploadDeletePopupComponent,
    resolve: {
      amortizationUpload: AmortizationUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploads'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
