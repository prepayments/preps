import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AboutPrepsRoutingModule } from './about-preps-routing.module';
import { AboutPrepsComponent } from './about-preps.component';
import { PrepsSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { ABOUT_PREPS_ROUTE } from 'app/preps/about-preps/about-preps.route';

@NgModule({
  declarations: [AboutPrepsComponent],
  imports: [PrepsSharedModule, RouterModule.forChild([ABOUT_PREPS_ROUTE]), CommonModule, AboutPrepsRoutingModule],
  exports: [AboutPrepsComponent],
  entryComponents: [AboutPrepsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AboutPrepsModule {}
