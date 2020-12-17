import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoStatus } from 'app/shared/model/po-status.model';
import { PoStatusService } from './po-status.service';

@Component({
  templateUrl: './po-status-delete-dialog.component.html',
})
export class PoStatusDeleteDialogComponent {
  poStatus?: IPoStatus;

  constructor(protected poStatusService: PoStatusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.poStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('poStatusListModification');
      this.activeModal.close();
    });
  }
}
