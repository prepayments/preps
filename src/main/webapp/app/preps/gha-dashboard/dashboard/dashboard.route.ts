import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { DashboardContainerComponent } from 'app/preps/gha-dashboard/dashboard/dashboard-container.component';

export const PREPS_DASHBOARD_ROUTE: Route = {
  path: 'preps/dashboard',
  component: DashboardContainerComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'Prepayments'
  },
  canActivate: [UserRouteAccessService]
};
