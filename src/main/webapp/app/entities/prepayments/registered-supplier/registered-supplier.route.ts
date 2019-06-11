import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { RegisteredSupplierService } from './registered-supplier.service';
import { RegisteredSupplierComponent } from './registered-supplier.component';
import { RegisteredSupplierDetailComponent } from './registered-supplier-detail.component';
import { RegisteredSupplierUpdateComponent } from './registered-supplier-update.component';
import { RegisteredSupplierDeletePopupComponent } from './registered-supplier-delete-dialog.component';
import { IRegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';

@Injectable({ providedIn: 'root' })
export class RegisteredSupplierResolve implements Resolve<IRegisteredSupplier> {
  constructor(private service: RegisteredSupplierService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRegisteredSupplier> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RegisteredSupplier>) => response.ok),
        map((registeredSupplier: HttpResponse<RegisteredSupplier>) => registeredSupplier.body)
      );
    }
    return of(new RegisteredSupplier());
  }
}

export const registeredSupplierRoute: Routes = [
  {
    path: '',
    component: RegisteredSupplierComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegisteredSuppliers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegisteredSupplierDetailComponent,
    resolve: {
      registeredSupplier: RegisteredSupplierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegisteredSuppliers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegisteredSupplierUpdateComponent,
    resolve: {
      registeredSupplier: RegisteredSupplierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegisteredSuppliers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegisteredSupplierUpdateComponent,
    resolve: {
      registeredSupplier: RegisteredSupplierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegisteredSuppliers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const registeredSupplierPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RegisteredSupplierDeletePopupComponent,
    resolve: {
      registeredSupplier: RegisteredSupplierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegisteredSuppliers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
