import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOutlet, Outlet } from 'app/shared/model/outlet.model';
import { OutletService } from './outlet.service';
import { OutletComponent } from './outlet.component';
import { OutletDetailComponent } from './outlet-detail.component';
import { OutletUpdateComponent } from './outlet-update.component';

@Injectable({ providedIn: 'root' })
export class OutletResolve implements Resolve<IOutlet> {
  constructor(private service: OutletService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOutlet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((outlet: HttpResponse<Outlet>) => {
          if (outlet.body) {
            return of(outlet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Outlet());
  }
}

export const outletRoute: Routes = [
  {
    path: '',
    component: OutletComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'covlabServerApp.outlet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OutletDetailComponent,
    resolve: {
      outlet: OutletResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.outlet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OutletUpdateComponent,
    resolve: {
      outlet: OutletResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.outlet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OutletUpdateComponent,
    resolve: {
      outlet: OutletResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.outlet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
