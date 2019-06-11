import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';
import { ServiceOutletDataEntryFileService } from './service-outlet-data-entry-file.service';
import { ServiceOutletDataEntryFileComponent } from './service-outlet-data-entry-file.component';
import { ServiceOutletDataEntryFileDetailComponent } from './service-outlet-data-entry-file-detail.component';
import { ServiceOutletDataEntryFileUpdateComponent } from './service-outlet-data-entry-file-update.component';
import { ServiceOutletDataEntryFileDeletePopupComponent } from './service-outlet-data-entry-file-delete-dialog.component';
import { IServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';

@Injectable({ providedIn: 'root' })
export class ServiceOutletDataEntryFileResolve implements Resolve<IServiceOutletDataEntryFile> {
  constructor(private service: ServiceOutletDataEntryFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceOutletDataEntryFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceOutletDataEntryFile>) => response.ok),
        map((serviceOutletDataEntryFile: HttpResponse<ServiceOutletDataEntryFile>) => serviceOutletDataEntryFile.body)
      );
    }
    return of(new ServiceOutletDataEntryFile());
  }
}

export const serviceOutletDataEntryFileRoute: Routes = [
  {
    path: '',
    component: ServiceOutletDataEntryFileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ServiceOutletDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceOutletDataEntryFileDetailComponent,
    resolve: {
      serviceOutletDataEntryFile: ServiceOutletDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutletDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceOutletDataEntryFileUpdateComponent,
    resolve: {
      serviceOutletDataEntryFile: ServiceOutletDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutletDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceOutletDataEntryFileUpdateComponent,
    resolve: {
      serviceOutletDataEntryFile: ServiceOutletDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutletDataEntryFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceOutletDataEntryFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceOutletDataEntryFileDeletePopupComponent,
    resolve: {
      serviceOutletDataEntryFile: ServiceOutletDataEntryFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutletDataEntryFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
