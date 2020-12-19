import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';

@Component({
  selector: 'jhi-device-overview-stats-detail',
  templateUrl: './device-overview-stats-detail.component.html',
})
export class DeviceOverviewStatsDetailComponent implements OnInit {
  deviceOverviewStats: IDeviceOverviewStats | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceOverviewStats }) => (this.deviceOverviewStats = deviceOverviewStats));
  }

  previousState(): void {
    window.history.back();
  }
}
