import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovlabServerSharedModule } from 'app/shared/shared.module';
import { TicketsComponent } from './tickets.component';
import { TicketsDetailComponent } from './tickets-detail.component';
import { TicketsUpdateComponent } from './tickets-update.component';
import { TicketsDeleteDialogComponent } from './tickets-delete-dialog.component';
import { ticketsRoute } from './tickets.route';

@NgModule({
  imports: [CovlabServerSharedModule, RouterModule.forChild(ticketsRoute)],
  declarations: [TicketsComponent, TicketsDetailComponent, TicketsUpdateComponent, TicketsDeleteDialogComponent],
  entryComponents: [TicketsDeleteDialogComponent],
})
export class CovlabServerTicketsModule {}
