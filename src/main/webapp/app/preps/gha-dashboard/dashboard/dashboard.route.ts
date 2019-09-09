import { Route } from '@angular/router';
import { DashboardComponent } from 'app/preps/gha-dashboard/dashboard/dashboard.component';
import { UserRouteAccessService } from 'app/core';

export const PREPS_DASHBOARD_ROUTE: Route = {
  path: 'preps/dashboard',
  component: DashboardComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'PREPS: DASHBOARD'
  },
  canActivate: [UserRouteAccessService]
};
