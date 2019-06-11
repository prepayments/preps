import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';
import { SupplierDataEntryFileService } from './supplier-data-entry-file.service';
import { SupplierDataEntryFileComponent } from './supplier-data-entry-file.component';
import { SupplierDataEntryFileDetailComponent } from './supplier-data-entry-file-detail.component';
import { SupplierDataEntryFileUpdateComponent } from './supplier-data-entry-file-update.component';
import { SupplierDataEntryFileDeletePopupComponent } from './supplier-data-entry-file-delete-dialog.component';
import { ISupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';

@Injectable({ providedIn: 'root' })
export class SupplierDataEntryFileResolve implements Resolve<ISupplierDataEntryFile> {
  constructor(private service: SupplierDataEntryFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplierDataEntryFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SupplierDataEntryFile>) => response.ok),
        map((supplierDataEntryFile: HttpResponse<SupplierDataEntryFile>) => supplierDataEntryFile.body)
      );
    }
    return of(new SupplierDataEntryFile());
  }
}

export const supplierDataEntryFileRoute: Routes = [
  {
    path: '',
    component: SupplierDataEntryFileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'SupplierDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupplierDataEntryFileDetailComponent,
    resolve: {
      supplierDataEntryFile: SupplierDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupplierDataEntryFileUpdateComponent,
    resolve: {
      supplierDataEntryFile: SupplierDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupplierDataEntryFileUpdateComponent,
    resolve: {
      supplierDataEntryFile: SupplierDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const supplierDataEntryFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SupplierDataEntryFileDeletePopupComponent,
    resolve: {
      supplierDataEntryFile: SupplierDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierDataEntryFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
