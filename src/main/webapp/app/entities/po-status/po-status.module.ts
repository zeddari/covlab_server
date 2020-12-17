import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovlabServerSharedModule } from 'app/shared/shared.module';
import { PoStatusComponent } from './po-status.component';
import { PoStatusDetailComponent } from './po-status-detail.component';
import { PoStatusUpdateComponent } from './po-status-update.component';
import { PoStatusDeleteDialogComponent } from './po-status-delete-dialog.component';
import { poStatusRoute } from './po-status.route';

@NgModule({
  imports: [CovlabServerSharedModule, RouterModule.forChild(poStatusRoute)],
  declarations: [PoStatusComponent, PoStatusDetailComponent, PoStatusUpdateComponent, PoStatusDeleteDialogComponent],
  entryComponents: [PoStatusDeleteDialogComponent],
})
export class CovlabServerPoStatusModule {}
