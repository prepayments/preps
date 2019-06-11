import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';
import { ReportRequestEventService } from './report-request-event.service';
import { ReportRequestEventComponent } from './report-request-event.component';
import { ReportRequestEventDetailComponent } from './report-request-event-detail.component';
import { ReportRequestEventUpdateComponent } from './report-request-event-update.component';
import { ReportRequestEventDeletePopupComponent } from './report-request-event-delete-dialog.component';
import { IReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';

@Injectable({ providedIn: 'root' })
export class ReportRequestEventResolve implements Resolve<IReportRequestEvent> {
  constructor(private service: ReportRequestEventService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReportRequestEvent> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReportRequestEvent>) => response.ok),
        map((reportRequestEvent: HttpResponse<ReportRequestEvent>) => reportRequestEvent.body)
      );
    }
    return of(new ReportRequestEvent());
  }
}

export const reportRequestEventRoute: Routes = [
  {
    path: '',
    component: ReportRequestEventComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ReportRequestEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReportRequestEventDetailComponent,
    resolve: {
      reportRequestEvent: ReportRequestEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportRequestEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReportRequestEventUpdateComponent,
    resolve: {
      reportRequestEvent: ReportRequestEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportRequestEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReportRequestEventUpdateComponent,
    resolve: {
      reportRequestEvent: ReportRequestEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportRequestEvents'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reportRequestEventPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReportRequestEventDeletePopupComponent,
    resolve: {
      reportRequestEvent: ReportRequestEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportRequestEvents'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
