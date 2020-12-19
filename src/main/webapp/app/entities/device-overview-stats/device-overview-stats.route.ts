import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeviceOverviewStats, DeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';
import { DeviceOverviewStatsService } from './device-overview-stats.service';
import { DeviceOverviewStatsComponent } from './device-overview-stats.component';
import { DeviceOverviewStatsDetailComponent } from './device-overview-stats-detail.component';
import { DeviceOverviewStatsUpdateComponent } from './device-overview-stats-update.component';

@Injectable({ providedIn: 'root' })
export class DeviceOverviewStatsResolve implements Resolve<IDeviceOverviewStats> {
  constructor(private service: DeviceOverviewStatsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceOverviewStats> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deviceOverviewStats: HttpResponse<DeviceOverviewStats>) => {
          if (deviceOverviewStats.body) {
            return of(deviceOverviewStats.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeviceOverviewStats());
  }
}

export const deviceOverviewStatsRoute: Routes = [
  {
    path: '',
    component: DeviceOverviewStatsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'covlabServerApp.deviceOverviewStats.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviceOverviewStatsDetailComponent,
    resolve: {
      deviceOverviewStats: DeviceOverviewStatsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.deviceOverviewStats.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviceOverviewStatsUpdateComponent,
    resolve: {
      deviceOverviewStats: DeviceOverviewStatsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.deviceOverviewStats.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviceOverviewStatsUpdateComponent,
    resolve: {
      deviceOverviewStats: DeviceOverviewStatsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covlabServerApp.deviceOverviewStats.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
