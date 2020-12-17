import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatusPO } from 'app/shared/model/status-po.model';
import { StatusPOService } from './status-po.service';

@Component({
  templateUrl: './status-po-delete-dialog.component.html',
})
export class StatusPODeleteDialogComponent {
  statusPO?: IStatusPO;

  constructor(protected statusPOService: StatusPOService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusPOService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statusPOListModification');
      this.activeModal.close();
    });
  }
}
