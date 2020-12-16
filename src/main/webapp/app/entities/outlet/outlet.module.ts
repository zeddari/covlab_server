import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovlabServerSharedModule } from 'app/shared/shared.module';
import { OutletComponent } from './outlet.component';
import { OutletDetailComponent } from './outlet-detail.component';
import { OutletUpdateComponent } from './outlet-update.component';
import { OutletDeleteDialogComponent } from './outlet-delete-dialog.component';
import { outletRoute } from './outlet.route';

@NgModule({
  imports: [CovlabServerSharedModule, RouterModule.forChild(outletRoute)],
  declarations: [OutletComponent, OutletDetailComponent, OutletUpdateComponent, OutletDeleteDialogComponent],
  entryComponents: [OutletDeleteDialogComponent],
})
export class CovlabServerOutletModule {}
