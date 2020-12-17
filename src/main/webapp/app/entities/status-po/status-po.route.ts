import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatusPO, StatusPO } from 'app/shared/model/status-po.model';
import { StatusPOService } from './status-po.service';
import { StatusPOComponent } from './status-po.component';
import { StatusPODetailComponent } from './status-po-detail.component';
import { StatusPOUpdateComponent } from './status-po-update.component';

@Injectable({ providedIn: 'root' })
export class StatusPOResolve implements Resolve<IStatusPO> {
  constructor(private service: StatusPOService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusPO> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statusPO: HttpResponse<StatusPO>) => {
          if (statusPO.body) {
            return of(statusPO.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusPO());
  }
}

export const statusPORoute: Routes = [
  {
    path: '',
    component: StatusPOComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'covlabServerApp.statusPO.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusPODetailComponent,
    resolve: {
      statusPO: StatusPOResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.statusPO.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusPOUpdateComponent,
    resolve: {
      statusPO: StatusPOResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.statusPO.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusPOUpdateComponent,
    resolve: {
      statusPO: StatusPOResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.statusPO.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
