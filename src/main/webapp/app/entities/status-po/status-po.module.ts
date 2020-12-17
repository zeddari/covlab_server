import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovlabServerSharedModule } from 'app/shared/shared.module';
import { StatusPOComponent } from './status-po.component';
import { StatusPODetailComponent } from './status-po-detail.component';
import { StatusPOUpdateComponent } from './status-po-update.component';
import { StatusPODeleteDialogComponent } from './status-po-delete-dialog.component';
import { statusPORoute } from './status-po.route';

@NgModule({
  imports: [CovlabServerSharedModule, RouterModule.forChild(statusPORoute)],
  declarations: [StatusPOComponent, StatusPODetailComponent, StatusPOUpdateComponent, StatusPODeleteDialogComponent],
  entryComponents: [StatusPODeleteDialogComponent],
})
export class CovlabServerStatusPOModule {}
