import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovlabServerSharedModule } from 'app/shared/shared.module';
import { DeviceOverviewStatsComponent } from './device-overview-stats.component';
import { DeviceOverviewStatsDetailComponent } from './device-overview-stats-detail.component';
import { DeviceOverviewStatsUpdateComponent } from './device-overview-stats-update.component';
import { DeviceOverviewStatsDeleteDialogComponent } from './device-overview-stats-delete-dialog.component';
import { deviceOverviewStatsRoute } from './device-overview-stats.route';

@NgModule({
  imports: [CovlabServerSharedModule, RouterModule.forChild(deviceOverviewStatsRoute)],
  declarations: [
    DeviceOverviewStatsComponent,
    DeviceOverviewStatsDetailComponent,
    DeviceOverviewStatsUpdateComponent,
    DeviceOverviewStatsDeleteDialogComponent,
  ],
  entryComponents: [DeviceOverviewStatsDeleteDialogComponent],
})
export class CovlabServerDeviceOverviewStatsModule {}
