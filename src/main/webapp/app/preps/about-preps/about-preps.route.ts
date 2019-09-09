import { Route } from '@angular/router';
import { AboutPrepsComponent } from 'app/preps/about-preps/about-preps.component';

export const ABOUT_PREPS_ROUTE: Route = {
  path: 'about/preps',
  component: AboutPrepsComponent,
  data: {
    authorities: [],
    pageTitle: 'Prepayments Management System'
  }
};
