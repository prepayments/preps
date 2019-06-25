import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';
import { AmortizationUploadFileService } from './amortization-upload-file.service';
import { AmortizationUploadFileComponent } from './amortization-upload-file.component';
import { AmortizationUploadFileDetailComponent } from './amortization-upload-file-detail.component';
import { AmortizationUploadFileUpdateComponent } from './amortization-upload-file-update.component';
import { AmortizationUploadFileDeletePopupComponent } from './amortization-upload-file-delete-dialog.component';
import { IAmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';

@Injectable({ providedIn: 'root' })
export class AmortizationUploadFileResolve implements Resolve<IAmortizationUploadFile> {
  constructor(private service: AmortizationUploadFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmortizationUploadFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmortizationUploadFile>) => response.ok),
        map((amortizationUploadFile: HttpResponse<AmortizationUploadFile>) => amortizationUploadFile.body)
      );
    }
    return of(new AmortizationUploadFile());
  }
}

export const amortizationUploadFileRoute: Routes = [
  {
    path: '',
    component: AmortizationUploadFileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AmortizationUploadFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmortizationUploadFileDetailComponent,
    resolve: {
      amortizationUploadFile: AmortizationUploadFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploadFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmortizationUploadFileUpdateComponent,
    resolve: {
      amortizationUploadFile: AmortizationUploadFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploadFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmortizationUploadFileUpdateComponent,
    resolve: {
      amortizationUploadFile: AmortizationUploadFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploadFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amortizationUploadFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmortizationUploadFileDeletePopupComponent,
    resolve: {
      amortizationUploadFile: AmortizationUploadFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AmortizationUploadFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
