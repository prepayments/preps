import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { PrepsSharedModule } from 'app/shared';
import { PrepsCoreModule } from 'app/core';
import { PrepsAppRoutingModule } from './app-routing.module';
import { PrepsHomeModule } from './home/home.module';
import { PrepsAccountModule } from './account/account.module';
import { PrepsEntityModule } from './entities/entity.module';
import * as moment from 'moment';
import { PrepsModule } from './preps/preps.module';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { GhaMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ErrorComponent } from './layouts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NgxWebstorageModule.forRoot({ prefix: 'gha', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: true,
      alertTimeout: 5000
    }),
    PrepsSharedModule.forRoot(),
    PrepsCoreModule,
    PrepsHomeModule,
    PrepsAccountModule,
    LoggerModule.forRoot({ serverLoggingUrl: '/api/logs', level: NgxLoggerLevel.DEBUG, serverLogLevel: NgxLoggerLevel.ERROR }),
    PrepsAccountModule,
    PrepsModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PrepsEntityModule,
    PrepsAppRoutingModule
  ],
  declarations: [GhaMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
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
  bootstrap: [GhaMainComponent]
})
export class PrepsAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
