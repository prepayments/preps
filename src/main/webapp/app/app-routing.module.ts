import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: './admin/admin.module#PrepsAdminModule'
        },
        {
          /*Add paths for report items*/
          path: 'data-export',
          loadChildren: './preps/data-display/data-export/data-export.module#DataExportModule'
        },
        {
          /*Add paths for report items*/
          path: 'reporting',
          loadChildren: './preps/reporting/reporting.module#ReportingModule'
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    )
  ],
  exports: [RouterModule]
})
export class PrepsAppRoutingModule {}
