import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReportType } from 'app/shared/model/reports/report-type.model';
import { ReportTypeService } from './report-type.service';
import { ReportTypeComponent } from './report-type.component';
import { ReportTypeDetailComponent } from './report-type-detail.component';
import { ReportTypeUpdateComponent } from './report-type-update.component';
import { ReportTypeDeletePopupComponent } from './report-type-delete-dialog.component';
import { IReportType } from 'app/shared/model/reports/report-type.model';

@Injectable({ providedIn: 'root' })
export class ReportTypeResolve implements Resolve<IReportType> {
  constructor(private service: ReportTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReportType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReportType>) => response.ok),
        map((reportType: HttpResponse<ReportType>) => reportType.body)
      );
    }
    return of(new ReportType());
  }
}

export const reportTypeRoute: Routes = [
  {
    path: '',
    component: ReportTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReportTypeDetailComponent,
    resolve: {
      reportType: ReportTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReportTypeUpdateComponent,
    resolve: {
      reportType: ReportTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReportTypeUpdateComponent,
    resolve: {
      reportType: ReportTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reportTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReportTypeDeletePopupComponent,
    resolve: {
      reportType: ReportTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
