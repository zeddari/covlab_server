import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';
import { DeviceOverviewStatsService } from './device-overview-stats.service';

@Component({
  templateUrl: './device-overview-stats-delete-dialog.component.html',
})
export class DeviceOverviewStatsDeleteDialogComponent {
  deviceOverviewStats?: IDeviceOverviewStats;

  constructor(
    protected deviceOverviewStatsService: DeviceOverviewStatsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deviceOverviewStatsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deviceOverviewStatsListModification');
      this.activeModal.close();
    });
  }
}
