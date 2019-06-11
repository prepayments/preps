import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import { NavigationRoutingModule } from './navigation-routing.module';
import { ReportMenuComponent } from './report-menu.component';
import { ExportMenuComponent } from './export-menu.component';
import { ImportMenuComponent } from './import-menu.component';
import { ModelsMenuComponent } from './models-menu.component';
import { AboutMenuComponent } from './about-menu.component';
import {PrepsSharedModule} from 'app/shared';
import {NotificationInterceptor} from 'app/blocks/interceptor/notification.interceptor';
import {AuthExpiredInterceptor} from 'app/blocks/interceptor/auth-expired.interceptor';
import {ErrorHandlerInterceptor} from 'app/blocks/interceptor/errorhandler.interceptor';
import {HTTP_INTERCEPTORS} from '@angular/common/http';

@NgModule({
    declarations: [ReportMenuComponent, ExportMenuComponent, ImportMenuComponent, ModelsMenuComponent, AboutMenuComponent],
    imports: [CommonModule, PrepsSharedModule, NavigationRoutingModule],
    exports: [ReportMenuComponent, ExportMenuComponent, ImportMenuComponent, ModelsMenuComponent, AboutMenuComponent],
    entryComponents: [ReportMenuComponent, ExportMenuComponent, ImportMenuComponent, ModelsMenuComponent, AboutMenuComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
})
export class NavigationModule {}
