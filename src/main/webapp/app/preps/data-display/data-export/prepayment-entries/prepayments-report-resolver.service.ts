import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { mergeMap, takeUntil } from 'rxjs/operators';
import { of } from 'rxjs/internal/observable/of';
import { HttpResponse } from '@angular/common/http';
import { EMPTY } from 'rxjs/internal/observable/empty';
import { PrepaymentsReportingService } from 'app/preps/data-display/data-export/prepayment-entries/prepayments-reporting.service';
import { IPrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

/**
 * Baad job with the current config. At least that route is blocked if the data is not present
 */
@Injectable({
  providedIn: 'root'
})
export class PrepaymentsReportResolverService implements Resolve<HttpResponse<IPrepaymentEntry[]>> {
  constructor(private prepaymentReportingService: PrepaymentsReportingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<IPrepaymentEntry[]>> | Observable<never> {
    return this.prepaymentReportingService.getEntities().pipe(
      mergeMap((item: HttpResponse<IPrepaymentEntry[]>) => {
        if (item) {
          return of(item);
        } else {
          this.router.navigate(['prepayment-entry']);
          return EMPTY;
        }
      })
    );
  }
}
