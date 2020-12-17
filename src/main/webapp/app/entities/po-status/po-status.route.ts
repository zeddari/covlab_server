import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPoStatus, PoStatus } from 'app/shared/model/po-status.model';
import { PoStatusService } from './po-status.service';
import { PoStatusComponent } from './po-status.component';
import { PoStatusDetailComponent } from './po-status-detail.component';
import { PoStatusUpdateComponent } from './po-status-update.component';

@Injectable({ providedIn: 'root' })
export class PoStatusResolve implements Resolve<IPoStatus> {
  constructor(private service: PoStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPoStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((poStatus: HttpResponse<PoStatus>) => {
          if (poStatus.body) {
            return of(poStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PoStatus());
  }
}

export const poStatusRoute: Routes = [
  {
    path: '',
    component: PoStatusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'covlabServerApp.poStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PoStatusDetailComponent,
    resolve: {
      poStatus: PoStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.poStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PoStatusUpdateComponent,
    resolve: {
      poStatus: PoStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.poStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PoStatusUpdateComponent,
    resolve: {
      poStatus: PoStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.poStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
